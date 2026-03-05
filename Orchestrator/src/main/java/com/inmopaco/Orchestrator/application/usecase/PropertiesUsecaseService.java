package com.inmopaco.Orchestrator.application.usecase;

import com.inmopaco.Orchestrator.infrastructure.queues.QueueService;
import com.inmopaco.shared.events.PropertiesEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertiesUsecaseService {
    @Autowired
    private QueueService queueService;

    public void getProperties(){
        queueService.publish("properties.get", PropertiesEvent.createEventMsg());
    }
}
