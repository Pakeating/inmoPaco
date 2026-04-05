package com.inmopaco.AuctionService.application.usecases.impl;

import com.inmopaco.AuctionService.application.dto.AuctionDetailsDTO;
import com.inmopaco.AuctionService.application.usecases.AuctionsPersistenceUsecase;
import com.inmopaco.AuctionService.domain.enums.ProcessingStatus;
import com.inmopaco.AuctionService.infrastructure.persistence.service.AuctionPersistenceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
public class AuctionsPersistenceUsecaseImpl implements AuctionsPersistenceUsecase {

    @Autowired
    private AuctionPersistenceService persistenceService;

    @Override
    public void smartSaveObtainedAuctions(List<AuctionDetailsDTO> auctionList) {

        try{
            log.info("[smartSaveObtainedAuctions] Saving {} Auctions", auctionList.size());
            
            //Smart-saving: check existing by boeIdentifier and update, else create new
            List<String> identifiers = auctionList.stream()
                    .map(AuctionDetailsDTO::getAuctionId)
                    .toList();

            Map<String, AuctionDetailsDTO> existingMap = persistenceService.findAllByAuctionIdIn(identifiers)
                    .stream()
                    .collect(Collectors.toMap(AuctionDetailsDTO::getAuctionId, dto -> dto));

            auctionList.forEach(dto -> {
                if (!existingMap.containsKey(dto.getAuctionId())) {
                    dto.setProcessingStatus(ProcessingStatus.OBTAINED);
                }
            });

            log.info("Saving {} auctions", auctionList.size());
            log.debug("Auction IDs: {}", auctionList.stream().map(AuctionDetailsDTO::getAuctionId).toList());

            persistenceService.saveAllAuctions(auctionList);

            long created = auctionList.size() - existingMap.size();
            
            log.info("[smartSaveObtainedAuctions] Finished smartSaving {} Auctions, {} new created", auctionList.size(), created);
        }catch (Exception e){
            log.warn("[smartSaveObtainedAuctions] Error saving auctions: {}", e.getMessage(), e);
            throw e;
        }

    }
}
