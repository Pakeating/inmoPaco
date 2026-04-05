package com.inmopaco.AuctionService.application.usecases.impl;

import com.inmopaco.AuctionService.application.usecases.AuctionsPersistenceUsecase;
import com.inmopaco.AuctionService.application.usecases.ProcessAuctionsUsecase;
import com.inmopaco.AuctionService.domain.enums.AuctionStatus;
import com.inmopaco.AuctionService.domain.enums.ProcessingStatus;
import com.inmopaco.AuctionService.infrastructure.messaging.queues.QueueService;
import com.inmopaco.AuctionService.infrastructure.pdf.PdfProcessingService;
import com.inmopaco.AuctionService.infrastructure.persistence.service.AuctionPersistenceService;
import com.inmopaco.shared.events.AIEvent;
import com.inmopaco.shared.events.AuctionsEvent;
import com.inmopaco.shared.events.GenericEventMsg;
import com.inmopaco.shared.events.enums.AIActions;
import com.inmopaco.shared.events.enums.Agents;
import com.inmopaco.shared.events.enums.AuctionsActions;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class ProcessAuctionsUsecaseImpl implements ProcessAuctionsUsecase {

    @Autowired
    private AuctionPersistenceService persistenceService;
    @Autowired
    private AuctionsPersistenceUsecase auctionsPersistenceUsecase;
    @Autowired
    private PdfProcessingService pdfProcessingService;
    @Autowired
    @Lazy
    private QueueService queueService;

    @Override
    public void processAuctions(AuctionsEvent event) {
        log.info("[ProcessAuctionsUsecase] START Processing auctions");
        var auctionsList = persistenceService.listAuctionsByProcessingStatus(ProcessingStatus.OBTAINED);

        log.info("[ProcessAuctionsUsecase] Found [" + auctionsList.size() + "] auctions to process");

        if (!auctionsList.isEmpty()) {
            Instant now = Instant.now();

            auctionsList.forEach(dto -> {
                if (dto.getDateOfEnd().isBefore(now.minus(1, ChronoUnit.DAYS))) {
                    // fecha de fin pasada con un dia de margen de mas, ya que el fin de subasta puede alargarse un dia
                    dto.setStatus(AuctionStatus.EXPIRED);
                } else if (dto.getDateOfStart().isAfter(now)) {
                    dto.setStatus(AuctionStatus.UPCOMING);
                } else {
                    dto.setStatus(AuctionStatus.ACTIVE);
                }
                dto.setProcessingStatus(ProcessingStatus.PARTIALLY_PROCESSED);
            });

            auctionsPersistenceUsecase.smartSaveObtainedAuctions(auctionsList);
        }

        var responseEvent = AuctionsEvent.createEventMsg(AuctionsActions.PARTIALLY_PROCESSED_AUCTIONS,
                "Processed [" + auctionsList.size() + "] auctions");

        responseEvent.setParentEventId(event.getEventId());
        publish(responseEvent, Agents.ORCHESTRATOR_SERVICE);


        log.info("[ProcessAuctionsUsecase] Processing documents");
        //guarda en un Map el id el el texto del pdf. formato de la key: "auctionId -> pdfUrl"
        auctionsList = persistenceService.listAuctionsByProcessingStatus(ProcessingStatus.PARTIALLY_PROCESSED);
        log.info("[ProcessAuctionsUsecase] Sending AI-Processing requests for {} auctions", auctionsList.size());
        List<String> sent = new ArrayList<>();
        List<String> failed = new ArrayList<>();

        //TODO: Revisar que pasa si no hay doc, nunca pasa a procesado?
        auctionsList.parallelStream().forEach(dto -> {
            if (dto.getDocuments() != null) {
                for (var doc : dto.getDocuments()) {
                    String docUrl = doc.getDocumentUrl();
                    try {
                        String key = dto.getAuctionId().concat(" -> ").concat(docUrl);
                        String value = pdfProcessingService.getTextFromPdfUrl(docUrl);

                        var requestAIEvent = AIEvent.createEventMsg(AIActions.GET_AUCTIONS_REPORT, key);
                        requestAIEvent.setContent(value);
                        publish(requestAIEvent, Agents.AI_SERVICE);
                        sent.add(key);
                    } catch (Exception e) {
                        log.error("[ProcessAuctionsUsecase] Error processing or sending PDF for auction {}: {}", dto.getAuctionId(), e.getMessage());
                        failed.add(dto.getAuctionId().concat(" -> ").concat(docUrl));
                    }
                }
            }
        });

        log.info("[ProcessAuctionsUsecase] Finished sending AI-Processing requests. Sent: {}, Failed: {}", sent.size(), failed.size());
        if (!failed.isEmpty()) {
            log.error("[ProcessAuctionsUsecase] Failed to process or send the following documents:");
            failed.forEach(doc -> log.warn("\\\t{}", doc));
        }

        log.info("[ProcessAuctionsUsecase] END Processing auctions");
    }

    @Override
    public void processAuction(String auctionId) {
        log.info("[ProcessAuctionsUsecase] START Processing specific auction: [{}]", auctionId);
        var auctionOpt = persistenceService.findByAuctionId(auctionId);

        if (auctionOpt.isEmpty()) {
            log.warn("[ProcessAuctionsUsecase] Auction not found with ID: [{}]", auctionId);
            return;
        }

        var dto = auctionOpt.get();
        Instant now = Instant.now();

        if (dto.getDateOfEnd().isBefore(now.minus(1, ChronoUnit.DAYS))) {
            dto.setStatus(AuctionStatus.EXPIRED);
        } else if (dto.getDateOfStart().isAfter(now)) {
            dto.setStatus(AuctionStatus.UPCOMING);
        } else {
            dto.setStatus(AuctionStatus.ACTIVE);
        }
        dto.setProcessingStatus(ProcessingStatus.PARTIALLY_PROCESSED);

        auctionsPersistenceUsecase.smartSaveObtainedAuctions(List.of(dto));

        var responseEvent = AuctionsEvent.createEventMsg(AuctionsActions.PARTIALLY_PROCESSED_AUCTIONS,
                "Processed auction [" + auctionId + "]");

        publish(responseEvent, Agents.ORCHESTRATOR_SERVICE);

        log.info("[ProcessAuctionsUsecase] Processing documents for auction [{}]", auctionId);
        
        if (dto.getDocuments() != null) {
            for (var doc : dto.getDocuments()) {
                String docUrl = doc.getDocumentUrl();
                try {
                    String key = dto.getAuctionId().concat(" -> ").concat(docUrl);
                    String value = pdfProcessingService.getTextFromPdfUrl(docUrl);

                    var requestAIEvent = AIEvent.createEventMsg(AIActions.GET_AUCTIONS_REPORT, key);
                    requestAIEvent.setContent(value);
                    publish(requestAIEvent, Agents.AI_SERVICE);
                } catch (Exception e) {
                    log.error("[ProcessAuctionsUsecase] Error processing or sending PDF for auction {}: {}", dto.getAuctionId(), e.getMessage());
                }
            }
        }
        log.info("[ProcessAuctionsUsecase] END Processing auction [{}]", auctionId);
    }

    @Transactional
    @Override
    public void saveAIProcessing(AIEvent event) {
        log.info("[ProcessAuctionsUsecase] START Saving AI processing for auction [{}]", event.getAuctionId());
        var keyParts = event.getAuctionId().split(" -> ");
        if (keyParts.length != 2) {
            log.error("[ProcessAuctionsUsecase] Invalid key format in AIEvent: {}", event.getAuctionId());
            return;
        }
        String auctionId = keyParts[0].trim();
        String docUrl = keyParts[1].trim();

        try {
            var auctionOpt = persistenceService.findByAuctionId(auctionId);
            if (auctionOpt.isPresent()) {
                var auction = auctionOpt.get();
                auction.setProcessingStatus(ProcessingStatus.PROCESSED);
                auction.getDocuments().forEach(doc -> {
                    if (doc.getDocumentUrl().trim().equals(docUrl)) {
                        doc.setDocAiAnalysis(event.getContent());
                    }
                });
                auctionsPersistenceUsecase.smartSaveObtainedAuctions(List.of(auction));
            }

            var responseEvent = AuctionsEvent.createEventMsg(AuctionsActions.PROCESSED_AUCTIONS, auctionId);
            responseEvent.setParentEventId(event.getEventId());
            publish(responseEvent, Agents.ORCHESTRATOR_SERVICE);
            log.info("[ProcessAuctionsUsecase] Successfully saved AI processing for auction [{}]", auctionId);
        } catch (Exception e) {
            log.error("[ProcessAuctionsUsecase] Error saving AI processing for auction [{}]: {}", auctionId, e.getMessage());
        }
    }


    private void publish(GenericEventMsg event, Agents destinedTo) {
        event.setProducedBy(Agents.AUCTIONS_SERVICE);
        switch (destinedTo) {
            case ORCHESTRATOR_SERVICE -> {
                event.setDestinedTo(Agents.ORCHESTRATOR_SERVICE);
                queueService.publish("auctions.response", (AuctionsEvent) event);
            }

            case AI_SERVICE -> {
                event.setDestinedTo(Agents.AI_SERVICE);
                queueService.publish("ai.get", (AIEvent) event);
            }
            default -> event.setDestinedTo(Agents.ORCHESTRATOR_SERVICE);
        }

    }
}
