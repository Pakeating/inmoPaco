package com.inmopaco.AuctionService.infrastructure.persistence.service.impl;

import com.inmopaco.AuctionService.application.dto.AuctionDetailsDTO;
import com.inmopaco.AuctionService.infrastructure.persistence.entity.AuctionEntity;
import com.inmopaco.AuctionService.infrastructure.persistence.mapper.AuctionRepositoryMapper;
import com.inmopaco.AuctionService.infrastructure.persistence.repository.AuctionRepository;
import com.inmopaco.AuctionService.infrastructure.persistence.service.AuctionPersistenceService;
import jakarta.transaction.Transactional;
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

    @Transactional
    @Override
    public void saveAllAuctions(List<AuctionDetailsDTO> auctionList) {
        List<AuctionEntity> entities = auctionList.stream()
                .map(dto -> mapper.toEntity(dto))
                .toList();
        repository.saveAll(entities);
    }

    @Transactional
    @Override
    //returns the number of new auctions created
    public long saveOrUpdateAuctions(List<AuctionDetailsDTO> auctionList) {

        //Smart-saving: check existing by boeIdentifier and update, else create new
        List<String> identifiers = auctionList.stream()
                .map(AuctionDetailsDTO::getAuctionId)
                .toList();

        Map<String, AuctionEntity> entityMap = repository.findAllByAuctionIdIn(identifiers)
                .stream()
                .collect(Collectors.toMap(AuctionEntity::getAuctionId, entity -> entity));

        List<AuctionEntity> entitiesToSave = auctionList.stream().map(dto -> {
            AuctionEntity existingEntity = entityMap.get(dto.getAuctionId());
            if (existingEntity != null) {
                // MapStruct actualizará los campos de 'existingEntity'
                //TODO: Revisar que el mapper no haga setLots(new ArrayList)
                mapper.updateEntityFromDTO(dto, existingEntity);
                return existingEntity;
            } else {
                return mapper.toEntity(dto);
            }
        }).toList();

        log.info("Saving {} auctions", entitiesToSave.size());
        log.debug("Auction IDs: {}", auctionList.stream().map(AuctionDetailsDTO::getAuctionId).toList());

        repository.saveAll(entitiesToSave);

        return auctionList.size() - entityMap.size();
    }

    @Override
    public List<AuctionDetailsDTO> listAllAuctions() {
        return repository.findAll().parallelStream().map(mapper::toDTO).toList();
    }

    @Override
    public List<String> listAuctionIdsByStatus(String status) {
        return repository.findAllByStatus(status).stream().map(AuctionEntity::getAuctionId).toList();
    }
}
