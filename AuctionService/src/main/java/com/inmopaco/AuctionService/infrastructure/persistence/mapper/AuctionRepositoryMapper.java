package com.inmopaco.AuctionService.infrastructure.persistence.mapper;

import com.inmopaco.AuctionService.application.dto.AuctionDTO;
import com.inmopaco.AuctionService.infrastructure.persistence.entity.AuctionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuctionRepositoryMapper {

    public AuctionEntity toEntity(AuctionDTO auction);

    public AuctionDTO toDTO(AuctionEntity auctionEntity);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(AuctionDTO dto, @MappingTarget AuctionEntity entity);
}
