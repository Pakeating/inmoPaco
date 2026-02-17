package com.inmopaco.AuctionService.infrastructure.persistence.service;

import com.inmopaco.AuctionService.application.dto.AuctionDetailsDTO;

import java.util.List;

public interface AuctionPersistenceService {

    void saveAuction(AuctionDetailsDTO auctionDTO);

    void saveAllAuctions(List<AuctionDetailsDTO> auctionList);

    long saveOrUpdateAuctions(List<AuctionDetailsDTO> auctionList);

    List<AuctionDetailsDTO> listAllAuctions();

    List<String> listAuctionIdsByStatus(String status);
}
