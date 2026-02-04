package com.inmopaco.AuctionService.application.dto;

import com.inmopaco.AuctionService.domain.model.Lot;
import lombok.Data;

import java.util.List;

@Data
public class AuctionDTO {
    private Long id;
    private String boeIdentifier;
    private String province;
    private String baseUrl;
    private String managingAuthority;
    private String currentStatus; //TODO: This should be an enum
    private List<LotDTO> lots;
}
