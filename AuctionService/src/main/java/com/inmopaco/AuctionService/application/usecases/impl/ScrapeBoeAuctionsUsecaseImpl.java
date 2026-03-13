package com.inmopaco.AuctionService.application.usecases.impl;

import com.inmopaco.AuctionService.application.dto.AuctionDetailsDTO;
import com.inmopaco.AuctionService.application.usecases.ScrapeBoeAuctionsUsecase;
import com.inmopaco.AuctionService.infrastructure.messaging.queues.QueueService;
import com.inmopaco.AuctionService.infrastructure.persistence.service.AuctionPersistenceService;
import com.inmopaco.AuctionService.infrastructure.scraper.AuctionScraperProviderService;
import com.inmopaco.shared.events.AuctionsEvent;
import com.inmopaco.shared.events.enums.AuctionsActions;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ScrapeBoeAuctionsUsecaseImpl implements ScrapeBoeAuctionsUsecase {

    @Autowired
    private AuctionScraperProviderService scraperService;

    @Autowired
    @Lazy
    private QueueService queueService;

    @Autowired
    private AuctionPersistenceService persistenceService;

    @Override
    public void scrapeBoeAuctions(AuctionsEvent event) {

        log.info("[ScrapeBoeAuctionsUsecase] START Auction scraping for event {} and code {}", event.getEventId(), event.getPayload());

        List<AuctionDetailsDTO> auctionList = scraperService.fetchSearchResults(event.getPayload());

        log.info("[ScrapeBoeAuctionsUsecase] Saving Auctions for event {}", event.getEventId());

        long created = persistenceService.saveOrUpdateAuctions(auctionList);

        var responseEvent = AuctionsEvent.createEventMsg(AuctionsActions.RETRIEVED_AUCTIONS, event.getPayload());
        responseEvent.setParentEventId(event.getEventId());

        queueService.publish("auctions.response", responseEvent);

        log.info("[ScrapeBoeAuctionsUsecase] END Auction scraping for event {} and code {}", event.getEventId(), event.getPayload());
    }
}

