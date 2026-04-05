package com.inmopaco.BFF.application.usecases;

import com.inmopaco.BFF.application.dto.AuctionDetailsDTO;
import com.inmopaco.BFF.application.dto.AuctionQueryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AuctionQueryUsecase {
    Page<AuctionDetailsDTO> search(AuctionQueryDTO querySpecs, Pageable pageable);

    Optional<AuctionDetailsDTO> searchById(String id);
}
