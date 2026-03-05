package com.inmopaco.Orchestrator.application.usecase;

import com.inmopaco.Orchestrator.infrastructure.queues.QueueService;
import com.inmopaco.shared.events.AuctionsEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionsUsecaseService {
    @Autowired
    private QueueService queueService;

    public void getAuctions(){
            queueService.publish("auctions.get", AuctionsEvent.createEventMsg());
    }
}
