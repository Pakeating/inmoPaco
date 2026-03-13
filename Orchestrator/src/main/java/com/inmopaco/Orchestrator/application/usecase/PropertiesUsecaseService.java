package com.inmopaco.Orchestrator.application.usecase;

import com.inmopaco.Orchestrator.infrastructure.queues.QueueService;
import com.inmopaco.shared.events.PropertiesEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import static com.inmopaco.shared.events.enums.PropertiesActions.GET_PROPERTIES;

@Service
@Log4j2
public class PropertiesUsecaseService {

    @Autowired
    @Lazy
    private QueueService queueService;

    public void getProperties(String propertiesPayload){
        queueService.publish("properties.get", PropertiesEvent.createEventMsg(GET_PROPERTIES, propertiesPayload));
    }

    public void receivedAuctionsResponse(PropertiesEvent event) {
        log.info("Received properties response for event {} with action {}", event.getEventId(), event.getAction());
    }
}
