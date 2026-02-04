package com.inmopaco.AuctionService.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.util.Map;

@Entity
public class LotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lot_id")
    private Long lotId;

    @Column(name = "lot_boe_number")
    private Long lotBoeNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "auction_value")
    private Double auctionValue;

    @Column(name = "current_bid")
    private Double currentBid;

    @Column(name = "cadastral_reference")
    private String cadastralReference;

    @Column(name = "cadastral_data")
    private Map<String, String> cadastralData;
}
