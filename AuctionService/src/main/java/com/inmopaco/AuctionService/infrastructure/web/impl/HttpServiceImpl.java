package com.inmopaco.AuctionService.infrastructure.web.impl;

import com.inmopaco.AuctionService.application.usecases.ScrapeBoeAuctionsUsecase;
import com.inmopaco.AuctionService.infrastructure.web.HttpService;
import com.inmopaco.shared.events.AuctionsEvent;
import com.inmopaco.shared.events.enums.AuctionsActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpServiceImpl implements HttpService {

    @Autowired
    private ScrapeBoeAuctionsUsecase scrapeBoeAuctionsUsecase;

    /// para pruebas
    @GetMapping("/scrape-auctions")
    @Override
    public void executeAuctionScraping(String auctionsPayload, AuctionsActions eventAction) {
        scrapeBoeAuctionsUsecase.scrapeBoeAuctions(AuctionsEvent.createEventMsg(eventAction, auctionsPayload));

    }
}
