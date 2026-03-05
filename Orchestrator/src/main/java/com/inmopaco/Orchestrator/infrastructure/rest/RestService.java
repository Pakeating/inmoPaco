package com.inmopaco.Orchestrator.infrastructure.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface RestService {
    @GetMapping("/orchestrator/auctions/launch-service")
    ResponseEntity<Object> executeGetAuctions();

    @GetMapping("/orchestrator/properties/launch-service")
    ResponseEntity<Object> executeGetProperties();

    @GetMapping("/orchestrator/queue-management/purgue")
    ResponseEntity<Object> purgueQueues() throws Exception;
}
