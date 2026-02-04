package com.inmopaco.AuctionService.infrastructure.scraper.providers.jsoup.chain.dto;

import com.inmopaco.AuctionService.infrastructure.scraper.dto.ScraperAuctionDetailsDTO;
import com.inmopaco.AuctionService.infrastructure.scraper.dto.ScraperAuctionSummaryDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JsoupChainContextDTO {
    private final ScraperAuctionSummaryDTO summary;
    private final ScraperAuctionDetailsDTO.ScraperAuctionDetailsDTOBuilder builder;
}
