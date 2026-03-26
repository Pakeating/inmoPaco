package com.inmopaco.AuctionService.application.usecases.impl;

import com.inmopaco.AuctionService.application.usecases.AuctionsPersistenceUsecase;
import com.inmopaco.AuctionService.application.usecases.ProcessAuctionsUsecase;
import com.inmopaco.AuctionService.domain.enums.AuctionStatus;
import com.inmopaco.AuctionService.domain.enums.ProcessingStatus;
import com.inmopaco.AuctionService.infrastructure.messaging.queues.QueueService;
import com.inmopaco.AuctionService.infrastructure.persistence.service.AuctionPersistenceService;
import com.inmopaco.shared.events.AuctionsEvent;
import com.inmopaco.shared.events.enums.AuctionsActions;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Log4j2
public class ProcessAuctionsUsecaseImpl implements ProcessAuctionsUsecase {

    @Autowired
    private AuctionPersistenceService persistenceService;
    @Autowired
    private AuctionsPersistenceUsecase auctionsPersistenceUsecase;
    @Autowired
    @Lazy
    private QueueService queueService;

    @Override
    public void processAuctions(AuctionsEvent event) {
        log.info("[ProcessAuctionsUsecase] START Processing auctions");
        var auctionsList = persistenceService.listAuctionsByProcessingStatus(ProcessingStatus.OBTAINED);
        log.info("[ProcessAuctionsUsecase] Found [" + auctionsList.size() + "] auctions to process");
        Instant now = Instant.now();

        auctionsList.forEach(dto->{
            if (dto.getDateOfEnd().isBefore(now.minus(1,ChronoUnit.DAYS))) {
                // fecha de fin pasada con un dia de margen de mas, ya que el fin de subasta puede alargarse un dia
                dto.setStatus(AuctionStatus.EXPIRED);
            } else if (dto.getDateOfStart().isAfter(now)) {
                dto.setStatus(AuctionStatus.UPCOMING);
            } else {
                dto.setStatus(AuctionStatus.ACTIVE);
            }
            dto.setProcessingStatus(ProcessingStatus.PARTIALLY_PROCESSED);
        });

        auctionsPersistenceUsecase.smartSaveAuctions(auctionsList);

        var responseEvent = AuctionsEvent.createEventMsg(AuctionsActions.PROCESSED_AUCTIONS,
                "Processed [" + auctionsList.size() + "] auctions");

        responseEvent.setParentEventId(event.getEventId());
        queueService.publish("auctions.response", responseEvent);

        log.info("[ProcessAuctionsUsecase] END Processing auctions");
    }

}
