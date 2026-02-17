package com.inmopaco.AuctionService.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "auctions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionEntity {

    @Id
    @Column(name = "auction_id")
    private String auctionId;

    private String type;

    @Column(name = "counting_account")
    private String countingAccount;

    @Column(name = "date_of_start")
    private String dateOfStart;

    @Column(name = "date_of_end")
    private String dateOfEnd;

    @Column(name = "claimed_amount")
    private String claimedAmount;

    @Column(name = "lots_number")
    private String lotsNumber;

    @Column(name = "boe_announcement")
    private String boeAnnouncement;

    @Column(name = "auction_value")
    private String auctionValue;

    private String appraisal;

    @Column(name = "minimum_bid")
    private String minimumBid;

    @Column(name = "bid_increments")
    private String bidIncrements;

    @Column(name = "deposit_amount")
    private String depositAmount;

    @ElementCollection
    @CollectionTable(name = "auction_documents", joinColumns = @JoinColumn(name = "auction_id"))
    @Column(name = "document_url")
    private List<String> documentUrls;

    @Column(name = "authority_code")
    private String authorityCode;

    @Column(name = "authority_description", columnDefinition = "TEXT")
    private String authorityDescription;

    @Column(name = "authority_address")
    private String authorityAddress;

    @Column(name = "authority_phone")
    private String authorityPhone;

    @Column(name = "authority_fax")
    private String authorityFax;

    @Column(name = "authority_email")
    private String authorityEmail;

    @Column(name = "goods_description", columnDefinition = "TEXT")
    private String goodsDescription;

    @Column(name = "cadastral_reference")
    private String cadastralReference;

    @Column(name = "property_address", columnDefinition = "TEXT")
    private String propertyAddress;

    private String cru;

    @Column(name = "registry_data", columnDefinition = "TEXT")
    private String registryData;

    private String city;
    private String province;

    @Column(name = "is_habitual_residence")
    private String isHabitualResidence;

    @Column(name = "is_visitable")
    private String isVisitable;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "possession_status")
    private String possessionStatus;

    @Column(name = "creditor_name")
    private String creditorName;

    @Column(name = "creditor_nif")
    private String creditorNif;

    @Column(name = "creditor_address")
    private String creditorAddress;

    @Column(name = "has_bids")
    private boolean hasBids;

    // Relación con Lotes
    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LotEntity> lots;

    @Column(name = "court_name")
    private String courtName;

    private String expediente;
    private String status;
}
