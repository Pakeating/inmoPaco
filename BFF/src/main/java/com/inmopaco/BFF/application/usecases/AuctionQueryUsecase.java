package com.inmopaco.BFF.application.usecases;

import com.inmopaco.BFF.application.dto.AuctionDetailsDTO;
import com.inmopaco.BFF.application.dto.AuctionQueryDTO;
import com.inmopaco.BFF.infrastructure.persistence.AuctionPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuctionQueryUsecase {

    @Autowired
    private AuctionPersistenceService auctionPersistenceService;

    public Page<AuctionDetailsDTO> search(AuctionQueryDTO querySpecs, Pageable pageable) {

        return auctionPersistenceService.findAuctions(querySpecs, pageable);
    }
}
