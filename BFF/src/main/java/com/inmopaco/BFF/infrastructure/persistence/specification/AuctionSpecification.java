package com.inmopaco.BFF.infrastructure.persistence.specification;

import com.inmopaco.BFF.application.dto.AuctionQueryDTO;
import com.inmopaco.BFF.infrastructure.persistence.entity.AuctionEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AuctionSpecification {
    public static Specification<AuctionEntity> getSpecification(AuctionQueryDTO dto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // IDS
            if (dto.getAuctionIds() != null && !dto.getAuctionIds().isEmpty()) {
                predicates.add(root.get("id").in(dto.getAuctionIds()));
            }

            // TYPE
            if (dto.getType() != null && !dto.getType().isBlank()) {
                predicates.add(cb.equal(root.get("type"), dto.getType()));
            }

            if (dto.getDateOfEnd() != null && !dto.getDateOfEnd().isBlank()) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateOfEnd"), dto.getDateOfEnd()));
            }

            if (dto.getCity() != null && !dto.getCity().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("city")), "%" + dto.getCity().toLowerCase() + "%"));
            }

            if (dto.getProvince() != null && !dto.getProvince().isBlank()) {
                predicates.add(cb.equal(root.get("province"), dto.getProvince()));
            }

            if (dto.getIsVisitable() != null && !dto.getIsVisitable().isBlank()) {
                predicates.add(cb.equal(root.get("isVisitable"), dto.getIsVisitable()));
            }

            if (dto.getHasBids() != null ) {
                predicates.add(cb.equal(root.get("hasBids"), dto.getHasBids()));
            }

            if (dto.getStatus() != null && !dto.getStatus().isBlank()) {
                predicates.add(cb.equal(root.get("status"), dto.getStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}