package com.inmopaco.PropertyService.infrastructure.messaging.queues.impl;

import com.inmopaco.PropertyService.application.usecases.ScrapePropertiesUsecase;
import com.inmopaco.PropertyService.infrastructure.messaging.queues.QueueService;
import com.inmopaco.PropertyService.infrastructure.messaging.queues.provider.GenericQueueProviderServiceImpl;
import com.inmopaco.shared.events.AuctionsEvent;
import com.inmopaco.shared.events.GenericEventMsg;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@AllArgsConstructor
public class QueueServiceImpl implements QueueService {

    @Autowired
    private GenericQueueProviderServiceImpl queueProvider;
    @Autowired
    private ScrapePropertiesUsecase scrapePropertiesUsecase;

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

//    TODO: Activar cuando el orchestrator genere el jetstream para properties
//    @PostConstruct
    public void subscribeToQueues(){
        subscribePersistent("properties.get", "propertyService", "get", AuctionsEvent.class, (event) -> scrapePropertiesUsecase.scrapeAllProperties() );
    }
}
