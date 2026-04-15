package com.inmopaco.AuctionService.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "auction_documents")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionDocumentEntity {

    @Id
    @Column(name = "document_url")
    @EqualsAndHashCode.Include
    private String documentUrl;

    @Column(name = "doc_ai_analysis", columnDefinition = "TEXT")
    private String docAiAnalysis;
}
