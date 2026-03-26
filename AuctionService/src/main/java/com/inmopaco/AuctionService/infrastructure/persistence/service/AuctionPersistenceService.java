package com.inmopaco.AuctionService.infrastructure.persistence.service;

import com.inmopaco.AuctionService.application.dto.AuctionDetailsDTO;
import com.inmopaco.AuctionService.domain.enums.AuctionStatus;
import com.inmopaco.AuctionService.domain.enums.ProcessingStatus;

import java.util.List;

public interface AuctionPersistenceService {

    void saveAuction(AuctionDetailsDTO auctionDTO);

    void saveAllAuctions(List<AuctionDetailsDTO> auctionList);

    long saveOrUpdateAuctions(List<AuctionDetailsDTO> auctionList);

    List<AuctionDetailsDTO> listAllAuctions();

    List<String> listAuctionIdsByStatus(AuctionStatus status);

    List<AuctionDetailsDTO> listAuctionsByProcessingStatus(ProcessingStatus status);
}
