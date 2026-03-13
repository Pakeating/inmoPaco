package com.inmopaco.Orchestrator.infrastructure.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface RestService {

    @GetMapping("/orchestrator/auctions/launch-service")
    ResponseEntity<Object> executeGetAuctions(@RequestParam String auctionsPayload);

    @GetMapping("/orchestrator/properties/launch-service")
    ResponseEntity<Object> executeGetProperties(@RequestParam String propertiesPayload);

    @GetMapping("/orchestrator/queue-management/purgue")
    ResponseEntity<Object> purgueQueues() throws Exception;

    @GetMapping("/orchestrator/queue-management/delete-consumer")
    ResponseEntity<Object> deleteConsumer(String stream, String subject) throws Exception;
}
