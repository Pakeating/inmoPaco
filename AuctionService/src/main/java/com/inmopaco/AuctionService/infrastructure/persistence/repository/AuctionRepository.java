package com.inmopaco.AuctionService.infrastructure.persistence.repository;

import com.inmopaco.AuctionService.infrastructure.persistence.entity.AuctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<AuctionEntity, String> {

    List<AuctionEntity> findAllByCurrentStatus(String currentStatus);

    List<AuctionEntity> findAllByBoeIdentifier(List<String> identifiers);

}
