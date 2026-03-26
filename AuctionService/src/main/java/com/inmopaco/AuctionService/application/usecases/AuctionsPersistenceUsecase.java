package com.inmopaco.AuctionService.application.usecases;

import com.inmopaco.AuctionService.application.dto.AuctionDetailsDTO;

import java.util.List;

public interface AuctionsPersistenceUsecase {
    void smartSaveAuctions(List<AuctionDetailsDTO> auctionList);
}
