package com.inmopaco.Orchestrator.infrastructure.rest.impl;

import com.inmopaco.Orchestrator.application.usecase.AuctionsUsecaseService;
import com.inmopaco.Orchestrator.application.usecase.PropertiesUsecaseService;
import com.inmopaco.Orchestrator.infrastructure.queues.provider.nats.management.NatsStreamManagementService;
import com.inmopaco.Orchestrator.infrastructure.rest.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestServiceImpl implements RestService {

    @Autowired
    private AuctionsUsecaseService auctionsUsecaseService;
    @Autowired
    private PropertiesUsecaseService propertiesUsecaseService;
    @Autowired
    private NatsStreamManagementService natsStreamManagementService;

    @GetMapping("/orchestrator/auctions/launch-service")
    @Override
    public ResponseEntity<Object> executeGetAuctions(){

        auctionsUsecaseService.getAuctions();
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/orchestrator/properties/launch-service")
    @Override
    public ResponseEntity<Object> executeGetProperties(){

        propertiesUsecaseService.getProperties();
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/orchestrator/queue-management/purge")
    @Override
    public ResponseEntity<Object> purgueQueues() throws Exception {
        natsStreamManagementService.purgeAllStreams();
        return ResponseEntity.ok().build();
    }
}
