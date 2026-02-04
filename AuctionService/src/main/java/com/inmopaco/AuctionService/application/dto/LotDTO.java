package com.inmopaco.AuctionService.application.dto;

import lombok.Data;

import java.util.Map;

@Data
public class LotDTO {
    private Long lotId;
    private Long lotBoeNumber;
    private String description;
    private Double auctionValue;
    private Double currentBid;
    private String cadastralReference;
    private Map<String, String> cadastralData;
}
