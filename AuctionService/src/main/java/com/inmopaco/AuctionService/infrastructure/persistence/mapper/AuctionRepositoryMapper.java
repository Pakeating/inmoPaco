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
        
        if (entity.getLots() == null) {
            entity.setLots(new java.util.HashSet<>());
        }

        // Creamos un mapa de los lotes actuales para actualizar sus valores en lugar de recrearlos
        java.util.Map<String, com.inmopaco.AuctionService.infrastructure.persistence.entity.LotEntity> currentLots = 
            entity.getLots().stream()
                  .collect(java.util.stream.Collectors.toMap(
                      com.inmopaco.AuctionService.infrastructure.persistence.entity.LotEntity::getLotId, 
                      l -> l, 
                      (l1, l2) -> l1));

        java.util.Set<com.inmopaco.AuctionService.infrastructure.persistence.entity.LotEntity> updatedLots = new java.util.HashSet<>();

        dto.getLots().forEach(newLot -> {
            com.inmopaco.AuctionService.infrastructure.persistence.entity.LotEntity lot = currentLots.get(newLot.getLotId());
            if (lot != null) {
                // Lote existente: actualizar campos para no perder el objeto de Hibernate (evita el fk_auction_id NULL)
                lot.setLotTitle(newLot.getLotTitle());
                lot.setAuctionValue(newLot.getAuctionValue());
                lot.setBidSteps(newLot.getBidSteps());
                lot.setDepositAmount(newLot.getDepositAmount());
                lot.setGoodsDescription(newLot.getGoodsDescription());
                lot.setCadastralReference(newLot.getCadastralReference());
                lot.setPropertyAddress(newLot.getPropertyAddress());
                lot.setCity(newLot.getCity());
                lot.setProvince(newLot.getProvince());
                lot.setPossessionStatus(newLot.getPossessionStatus());
                lot.setPostalCode(newLot.getPostalCode());
                lot.setIsHabitualResidence(newLot.getIsHabitualResidence());
                lot.setIsVisitable(newLot.getIsVisitable());
            } else {
                // Lote nuevo
                lot = lotMapper.toEntity(newLot);
            }
            // Asegurar que la referencia a la subasta padre esté siempre asignada
            lot.setAuction(entity);
            updatedLots.add(lot);
        });

        entity.getLots().retainAll(updatedLots); // Eliminamos lotes que ya no existan en el DTO
        entity.getLots().addAll(updatedLots);    // Añadimos los lotes nuevos
    }
}
