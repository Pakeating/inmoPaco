package com.inmopaco.AuctionService.infrastructure.persistence.service;

import com.inmopaco.AuctionService.application.dto.AuctionDTO;

import java.util.List;

public interface AuctionPersistenceService {

    void saveAuction(AuctionDTO auctionDTO);

    void saveAllAuctions(List<AuctionDTO> auctionList);

    long saveOrUpdateAuctions(List<AuctionDTO> auctionList);

    List<AuctionDTO> listAllAuctions();

    List<String> listAuctionIdentifiersByStatus(String status);
}
