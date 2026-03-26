package com.inmopaco.AuctionService.infrastructure.messaging.queues.impl;

import com.inmopaco.AuctionService.application.usecases.ScrapeBoeAuctionsUsecase;
import com.inmopaco.AuctionService.application.usecases.impl.ProcessAuctionsUsecaseImpl;
import com.inmopaco.AuctionService.infrastructure.messaging.queues.QueueService;
import com.inmopaco.AuctionService.infrastructure.messaging.queues.provider.GenericQueueProviderServiceImpl;
import com.inmopaco.shared.events.AuctionsEvent;
import com.inmopaco.shared.events.GenericEventMsg;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Consumer;

@Service
@Log4j2
@AllArgsConstructor
public class QueueServiceImpl implements QueueService {

    @Autowired
    private final GenericQueueProviderServiceImpl queueProvider;

    @Autowired
    @Lazy
    private ScrapeBoeAuctionsUsecase scrapeBoeAuctionsUsecase;
    @Autowired
    @Lazy
    private ProcessAuctionsUsecaseImpl processAuctionsUsecase;

    @Override
    public <EventMsg extends GenericEventMsg> void publish(String subject, EventMsg eventMsg) {
        if(eventMsg.isPersistent()) {
            queueProvider.publish(subject, eventMsg);
        } else {
            queueProvider.publishPersistent(subject, eventMsg);
        }
    }

    @Override
    public <EventMsg extends GenericEventMsg> void subscribe(String subject, String queueGroup, Class<EventMsg> targetClass, Consumer<EventMsg> handler) {
        queueProvider.subscribe(subject, queueGroup, targetClass, handler);
    }

    @Override
    public <EventMsg extends GenericEventMsg> void subscribePersistent(String subject, String durableName, String queueGroup, Class<EventMsg> targetClass, Consumer<EventMsg> handler) {
        queueProvider.subscribePersistent(subject, durableName, queueGroup, targetClass, handler);
    }

    //TODO: Las colas no deberian estar hardcodeadas
    @PostConstruct
    public void subscribeToQueues(){
        subscribePersistent("auctions.get",
                "AuctionsService",
                "get",
                AuctionsEvent.class,
                this::auctionsEventHandler
        );
    }

    @Async
    private void auctionsEventHandler(AuctionsEvent event) {
        log.info("Received Auctions Event {} with action {}", event.getEventId(), event.getAction());
        event.consumed(LocalDateTime.now());

        switch (event.getAction()) {
            case GET_AUCTIONS -> scrapeBoeAuctionsUsecase.scrapeBoeAuctions(event);
            case PROCESS_AUCTIONS -> processAuctionsUsecase.processAuctions(event);

            default -> throw new UnsupportedOperationException("Action not implemented: " + event.getAction());
        }
    }
}
