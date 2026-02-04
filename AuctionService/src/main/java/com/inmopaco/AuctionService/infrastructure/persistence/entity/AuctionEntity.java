package com.inmopaco.AuctionService.infrastructure.persistence.entity;

import com.inmopaco.AuctionService.domain.model.Lot;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class AuctionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "current_status")
    private String currentStatus; //TODO: This should be an enum

    @Column(name = "boe_Identifier", unique = true, nullable = false)
    private String boeIdentifier;

    @Column(name = "province")
    private String province;

    @Column(name = "url")
    private String baseUrl;

    @Column(name = "managing_authority")
    private String managingAuthority;

    @Column(name = "fk_lots")
    private List<Lot> lots;
}
