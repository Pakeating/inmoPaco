package com.inmopaco.AuctionService.application.usecases.impl;

import com.inmopaco.AuctionService.application.dto.AuctionDetailsDTO;
import com.inmopaco.AuctionService.application.usecases.AuctionsPersistenceUsecase;
import com.inmopaco.AuctionService.infrastructure.persistence.service.AuctionPersistenceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class AuctionsPersistenceUsecaseImpl implements AuctionsPersistenceUsecase {
    @Autowired
    private AuctionPersistenceService persistenceService;

    @Override
    public void smartSaveAuctions(List<AuctionDetailsDTO> auctionList) {

        //TODO: CAMBIAR LA LOGICA DEL SMARTSAVE DE INFRA A USECASE
        try{
            log.info("[smartSaveAuctions] Saving {} Auctions", auctionList.size());
            long created = persistenceService.saveOrUpdateAuctions(auctionList);
            log.info("[smartSaveAuctions] Finished smartSaving {} Auctions", auctionList.size());
        }catch (Exception e){
            log.warn("[smartSaveAuctions] Error saving auctions: {}", e.getMessage(), e);
            throw e;
        }

    }
}
