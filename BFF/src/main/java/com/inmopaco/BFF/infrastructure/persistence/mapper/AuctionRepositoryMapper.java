package com.inmopaco.BFF.infrastructure.persistence.mapper;

import com.inmopaco.BFF.application.dto.AuctionDetailsDTO;
import com.inmopaco.BFF.infrastructure.persistence.entity.AuctionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuctionRepositoryMapper {

    public AuctionEntity toEntity(AuctionDetailsDTO auction);

    public AuctionDetailsDTO toDTO(AuctionEntity auctionEntity);

}
