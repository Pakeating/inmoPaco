package com.inmopaco.AuctionService.infrastructure.restClient.impl;

import com.inmopaco.AuctionService.application.usecases.ScrapeBoeAuctionsUsecase;
import com.inmopaco.AuctionService.infrastructure.restClient.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpServiceImpl implements HttpService {

    @Autowired
    private ScrapeBoeAuctionsUsecase scrapeBoeAuctionsUsecase;

    @Override
    @GetMapping("/scrape-auctions")
    public void executeAuctionScraping() {
        scrapeBoeAuctionsUsecase.scrapeAllBoeAuctions();
    }
}
