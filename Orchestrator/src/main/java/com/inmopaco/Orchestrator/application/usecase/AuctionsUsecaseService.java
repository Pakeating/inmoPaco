package com.inmopaco.Orchestrator.application.usecase;

import com.inmopaco.Orchestrator.infrastructure.queues.QueueService;
import com.inmopaco.shared.events.AuctionsEvent;
import com.inmopaco.shared.events.enums.AuctionsActions;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AuctionsUsecaseService {
    @Value("${nats.subjects.publisher.auctions.get:auctions.get}")
    private String publishAuctionsSubject;

    @Autowired
    @Lazy
    private QueueService queueService;

    public void getAuctions(AuctionsEvent event) {
        queueService.publish(publishAuctionsSubject, event);
    }
    public void processAuctions(AuctionsEvent event) {
        queueService.publish(publishAuctionsSubject, event);
    }

    public void receivedGetAuctionsResponse(AuctionsEvent event) {
        log.info("Received auctions response for event {} with action {} and parent: {}", event.getEventId(), event.getAction(), event.getParentEventId());
        log.info("Sending AuctionsProcessing order");

        var childEvent = AuctionsEvent.createEventMsg(AuctionsActions.PROCESS_AUCTIONS);
        childEvent.setParentEventId(event.getEventId());
        processAuctions(childEvent);
    }

    public void receivedProcessedAuctionsResponse(AuctionsEvent event) {
        log.info("Received auctions response for event {} with action {} and parent: {}", event.getEventId(), event.getAction(), event.getParentEventId());
    }
}