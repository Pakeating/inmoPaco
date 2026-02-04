package com.inmopaco.AuctionService.application.usecases.impl;

import com.inmopaco.AuctionService.application.dto.AuctionDTO;
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
        List<String> provinceCodes = List.of("28", "08", "41");

        log.info("Starting auction scraping...");

//        List<AuctionDTO> auctionList = provinceCodes.stream()
//                .flatMap(code -> scraperService.fetchSearchResults(code).stream())
//                .toList();
//
//        long created = persistenceService.saveOrUpdateAuctions(auctionList);
//
//        log.info("Scraping Ended. Total auctions processed: {}, New auctions created: {}", auctionList.size(), created);
//        TODO: enviar notificacion de nuevas subastas?
    }
}

