package com.inmopaco.Orchestrator.application.usecase;

import com.inmopaco.Orchestrator.infrastructure.queues.QueueService;
import com.inmopaco.shared.events.AuctionsEvent;
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
    private int counter = 0;

    public void getAuctions(AuctionsEvent event) {
        queueService.publish(publishAuctionsSubject, event);
    }

    public void receivedAuctionsResponse(AuctionsEvent event) {
        counter++;
        log.info("Received auctions response for event {} with action {}, event number: {}", event.getEventId(), event.getAction(), counter);
    }
}