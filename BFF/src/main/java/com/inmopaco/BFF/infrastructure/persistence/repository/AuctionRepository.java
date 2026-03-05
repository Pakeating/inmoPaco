package com.inmopaco.BFF.infrastructure.persistence.repository;

import com.inmopaco.BFF.infrastructure.persistence.entity.AuctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<AuctionEntity, String>, JpaSpecificationExecutor<AuctionEntity> {

    List<AuctionEntity> findAllByStatus(String currentStatus);

    List<AuctionEntity> findAllByAuctionIdIn(List<String> auctionId);

    List<AuctionEntity> findByCityIgnoreCase(String city);

}
