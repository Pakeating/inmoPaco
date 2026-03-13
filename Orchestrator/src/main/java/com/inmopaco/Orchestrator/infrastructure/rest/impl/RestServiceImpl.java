package com.inmopaco.Orchestrator.infrastructure.rest.impl;

import com.inmopaco.Orchestrator.application.usecase.AuctionsUsecaseService;
import com.inmopaco.Orchestrator.application.usecase.PropertiesUsecaseService;
import com.inmopaco.Orchestrator.infrastructure.queues.provider.nats.management.NatsStreamManagementService;
import com.inmopaco.Orchestrator.infrastructure.rest.RestService;
import com.inmopaco.Orchestrator.infrastructure.schedulers.AuctionSchedulers;
import com.inmopaco.shared.events.AuctionsEvent;
import com.inmopaco.shared.events.enums.AuctionsActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestServiceImpl implements RestService {

    @Autowired
    private AuctionsUsecaseService auctionsUsecaseService;
    @Autowired
    private AuctionSchedulers auctionSchedulers;

    @Autowired
    private PropertiesUsecaseService propertiesUsecaseService;
    @Autowired
    private NatsStreamManagementService natsStreamManagementService;

    @GetMapping("/orchestrator/auctions/launch-service")
    @Override
    public ResponseEntity<Object> executeGetAuctions(@RequestParam String auctionsPayload){

        auctionsUsecaseService.getAuctions(AuctionsEvent.createEventMsg(AuctionsActions.GET_AUCTIONS, auctionsPayload));
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/orchestrator/auctions/launch-scheduler")
    public ResponseEntity<Object> executeScheduler(){
        auctionSchedulers.scrapeBoeAuctions();
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/orchestrator/properties/launch-service")
    @Override
    public ResponseEntity<Object> executeGetProperties(@RequestParam String propertiesPayload) {

        propertiesUsecaseService.getProperties(propertiesPayload);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/orchestrator/queue-management/purge")
    @Override
    public ResponseEntity<Object> purgueQueues() throws Exception {
        natsStreamManagementService.purgeAllStreams();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/orchestrator/queue-management/delete-consumer")
    @Override
    public ResponseEntity<Object> deleteConsumer(String stream, String subject) throws Exception {
        natsStreamManagementService.deleteConsumer(stream, subject);
        return ResponseEntity.ok().build();
    }
}
