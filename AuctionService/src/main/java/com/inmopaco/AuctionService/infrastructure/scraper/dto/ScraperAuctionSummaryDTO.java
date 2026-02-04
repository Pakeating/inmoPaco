package com.inmopaco.AuctionService.infrastructure.scraper.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScraperAuctionSummaryDTO {
    private String boeIdentifier;
    private String courtName;
    private String expediente;
    private String status;
    private String deadline;
    private String description;
    private String detailUrl;
}
