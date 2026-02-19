package com.inmopaco.Orchestrator.application.usecase;

import com.inmopaco.Orchestrator.domain.events.GetAuctionsEvent;
import com.inmopaco.Orchestrator.infrastructure.queues.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionsUsecaseService {
    @Autowired
    private QueueService queueService;

    public void GetAuctions(){
            queueService.publish("subject", GetAuctionsEvent.createEventMsg());
    }
}
