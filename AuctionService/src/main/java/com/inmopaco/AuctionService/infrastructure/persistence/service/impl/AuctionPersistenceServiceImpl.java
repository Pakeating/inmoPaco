package com.inmopaco.AuctionService.infrastructure.persistence.service.impl;

import com.inmopaco.AuctionService.application.dto.AuctionDetailsDTO;
import com.inmopaco.AuctionService.infrastructure.persistence.entity.AuctionEntity;
import com.inmopaco.AuctionService.infrastructure.persistence.mapper.AuctionRepositoryMapper;
import com.inmopaco.AuctionService.infrastructure.persistence.repository.AuctionRepository;
import com.inmopaco.AuctionService.infrastructure.persistence.service.AuctionPersistenceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
public class AuctionPersistenceServiceImpl implements AuctionPersistenceService {

    @Autowired
    private AuctionRepository repository;

    @Autowired
    private AuctionRepositoryMapper mapper;

    @Override
    public void saveAuction(AuctionDetailsDTO auctionDTO) {
        repository.save(mapper.toEntity(auctionDTO));
    }

    @Override
    public void saveAllAuctions(List<AuctionDetailsDTO> auctionList) {
        List<AuctionEntity> entities = auctionList.stream()
                .map(dto -> mapper.toEntity(dto))
                .toList();
        repository.saveAll(entities);
    }

    @Override
    //returns the number of new auctions created
    public long saveOrUpdateAuctions(List<AuctionDetailsDTO> auctionList) {

        //Smart-saving: check existing by boeIdentifier and update, else create new
        List<String> identifiers = auctionList.stream()
                .map(AuctionDetailsDTO::getAuctionId)
                .toList();

        Map<String, AuctionEntity> entityMap = repository.findAllByBoeIdentifier(identifiers)
                .stream()
                .collect(Collectors.toMap(AuctionEntity::getBoeIdentifier, entity -> entity));

        List <AuctionEntity> auctionEntities = auctionList.stream().map(dto -> {
            AuctionEntity entity = entityMap.get(dto.getAuctionId());
            if (entity != null) {
                // Update existing entity without losing its ID
                mapper.updateEntityFromDTO(dto, entity);
                return entity;
            } else {
                // New entity
                return mapper.toEntity(dto);
            }
        }).toList();

        List<AuctionEntity> entities = auctionList.stream()
                .map(dto -> mapper.toEntity(dto))
                .toList();
        repository.saveAll(entities);

        return auctionList.size()-entityMap.size();
    }

    @Override
    public List<AuctionDetailsDTO> listAllAuctions() {
        return repository.findAll().parallelStream().map(mapper::toDTO).toList();
    }

    @Override
    public List<String> listAuctionIdentifiersByStatus(String status) {
        return repository.findAllByCurrentStatus(status).stream().map(AuctionEntity::getBoeIdentifier).toList();
    }
}
