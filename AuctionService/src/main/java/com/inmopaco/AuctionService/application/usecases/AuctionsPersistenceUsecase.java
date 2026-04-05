package com.inmopaco.AuctionService.application.usecases;

import com.inmopaco.AuctionService.application.dto.AuctionDetailsDTO;

import java.util.List;

public interface AuctionsPersistenceUsecase {
    void smartSaveObtainedAuctions(List<AuctionDetailsDTO> auctionList);
}
