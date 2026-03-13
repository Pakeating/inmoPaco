package com.inmopaco.AuctionService.infrastructure.persistence.mapper;

import com.inmopaco.AuctionService.application.dto.AuctionDetailsDTO;
import com.inmopaco.AuctionService.infrastructure.persistence.entity.AuctionEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class AuctionRepositoryMapper {

    @Autowired
    private LotRepositoryMapper lotMapper;

    public abstract AuctionEntity toEntity(AuctionDetailsDTO auction);

    public abstract AuctionDetailsDTO toDTO(AuctionEntity auctionEntity);

    @Mapping(target = "lots", ignore = true)
    public abstract void updateEntityFromDTO(AuctionDetailsDTO dto, @MappingTarget AuctionEntity entity);

    @AfterMapping
    void mergeLots(AuctionDetailsDTO dto, @MappingTarget AuctionEntity entity) {
        if (dto.getLots() == null) return;

        dto.getLots().forEach(newLot -> {
            var lot = lotMapper.toEntity(newLot);
            entity.getLots().remove(lot); // Quita el viejo si existe
            entity.getLots().add(lot);    // Añade el nuevo (con datos actualizados)
        });
    }
}
