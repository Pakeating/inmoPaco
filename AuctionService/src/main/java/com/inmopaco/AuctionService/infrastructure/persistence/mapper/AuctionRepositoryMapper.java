package com.inmopaco.AuctionService.infrastructure.persistence.mapper;

import com.inmopaco.AuctionService.application.dto.AuctionDetailsDTO;
import com.inmopaco.AuctionService.infrastructure.persistence.entity.AuctionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuctionRepositoryMapper {

    public AuctionEntity toEntity(AuctionDetailsDTO auction);

    public AuctionDetailsDTO toDTO(AuctionEntity auctionEntity);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(AuctionDetailsDTO dto, @MappingTarget AuctionEntity entity);
}
