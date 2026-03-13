package com.inmopaco.Orchestrator.application.usecase;

import com.inmopaco.Orchestrator.infrastructure.queues.QueueService;
import com.inmopaco.shared.events.AuctionsEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AuctionsUsecaseService {
    @Autowired
    @Lazy
    private QueueService queueService;

    public void getAuctions(AuctionsEvent event) {
        queueService.publish("auctions.get", event);
    }

    public void receivedAuctionsResponse(AuctionsEvent event) {
        log.info("Received auctions response for event {} with action {}", event.getEventId(), event.getAction());
    }
}