package com.inmopaco.AuctionService.infrastructure.web;

import com.inmopaco.shared.events.enums.AuctionsActions;
import org.springframework.web.bind.annotation.GetMapping;

public interface HttpService  {

    @GetMapping("/scrape-auctions")
    void executeAuctionScraping(String auctionsPayload, AuctionsActions eventAction);
}
