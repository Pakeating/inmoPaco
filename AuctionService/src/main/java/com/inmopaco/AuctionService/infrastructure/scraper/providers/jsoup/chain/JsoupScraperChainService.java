package com.inmopaco.AuctionService.infrastructure.scraper.providers.jsoup.chain;

import com.inmopaco.AuctionService.infrastructure.scraper.dto.ScraperAuctionDetailsDTO;
import com.inmopaco.AuctionService.infrastructure.scraper.dto.ScraperAuctionSummaryDTO;

public interface JsoupScraperChainService {

    ScraperAuctionDetailsDTO execute();

    JsoupScraperChainService add(JsoupScraperChainNode node);

    JsoupScraperChainService create(ScraperAuctionSummaryDTO summary);
}
