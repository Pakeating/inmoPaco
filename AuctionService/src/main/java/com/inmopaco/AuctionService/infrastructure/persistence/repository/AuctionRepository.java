package com.inmopaco.AuctionService.infrastructure.persistence.repository;

import com.inmopaco.AuctionService.domain.enums.AuctionStatus;
import com.inmopaco.AuctionService.domain.enums.ProcessingStatus;
import com.inmopaco.AuctionService.infrastructure.persistence.entity.AuctionEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<AuctionEntity, String> {

    List<AuctionEntity> findAllByStatus(AuctionStatus status);

    List<AuctionEntity> findAllByAuctionIdIn(List<String> auctionId);

    List<AuctionEntity> findByCityIgnoreCase(String city);

    @EntityGraph(attributePaths = {"documentUrls", "lots"})
    List<AuctionEntity> findAllByProcessingStatus(ProcessingStatus processingStatus);

}
