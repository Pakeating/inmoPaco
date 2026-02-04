package com.inmopaco.AuctionService.infrastructure.persistence.repository;

import com.inmopaco.AuctionService.infrastructure.persistence.entity.AuctionEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
@Primary
public class AuctionRepositoryMock implements AuctionRepository {


    @Override
    public List<AuctionEntity> findAllByCurrentStatus(String currentStatus) {
        return List.of();
    }

    @Override
    public List<AuctionEntity> findAllByBoeIdentifier(List<String> identifiers) {
        return List.of();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends AuctionEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends AuctionEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<AuctionEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public AuctionEntity getOne(String s) {
        return null;
    }

    @Override
    public AuctionEntity getById(String s) {
        return null;
    }

    @Override
    public AuctionEntity getReferenceById(String s) {
        return null;
    }

    @Override
    public <S extends AuctionEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends AuctionEntity> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends AuctionEntity> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends AuctionEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends AuctionEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends AuctionEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends AuctionEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends AuctionEntity> S save(S entity) {
        return null;
    }

    @Override
    public <S extends AuctionEntity> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<AuctionEntity> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<AuctionEntity> findAll() {
        return List.of();
    }

    @Override
    public List<AuctionEntity> findAllById(Iterable<String> strings) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(AuctionEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends AuctionEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<AuctionEntity> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<AuctionEntity> findAll(Pageable pageable) {
        return null;
    }
}
