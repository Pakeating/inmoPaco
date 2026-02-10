package com.inmopaco.AuctionService.application.usecases.impl;

import com.inmopaco.AuctionService.application.dto.AuctionDetailsDTO;
import com.inmopaco.AuctionService.application.usecases.ScrapeBoeAuctionsUsecase;
import com.inmopaco.AuctionService.infrastructure.persistence.service.AuctionPersistenceService;
import com.inmopaco.AuctionService.infrastructure.scraper.AuctionScraperProviderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ScrapeBoeAuctionsUsecaseImpl implements ScrapeBoeAuctionsUsecase {

    @Autowired
    private AuctionScraperProviderService scraperService;

    @Autowired
    private AuctionPersistenceService persistenceService;

    @Override
    public void scrapeAllBoeAuctions() {

        // TODO: ESTO TENDRIA QUE SER UN ENUM DE DOMINIO CON TODAS LAS PROVINCIAS

        log.info("[ScrapeBoeAuctionsUsecase] START Auction scraping...");

        List<AuctionDetailsDTO> auctionList = scraperService.fetchSearchResults();

//        long created = persistenceService.saveOrUpdateAuctions(auctionList);
//
        log.info("[ScrapeBoeAuctionsUsecase] END Auction scraping");
//        TODO: enviar notificacion de nuevas subastas?
    }
}

