package com.inmopaco.Orchestrator.infrastructure.queues.provider.nats.config;

import com.inmopaco.Orchestrator.infrastructure.queues.provider.nats.management.NatsStreamManagementService;
import io.nats.client.Connection;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NatsStreamConfig {

    private final Connection natsConnection;

    @Autowired
    private NatsStreamManagementService natsStreamManagementService;

    //Configuracion para crear streams de NATS para persistencia de msg
    //TODO: se hace solo uno, pero tiene que conectarse a todos los que haya en un fichero de configuracion o algo
    @PostConstruct
    public void createStream() throws Exception {
        natsStreamManagementService.createStream(natsConnection, "AUCTIONS_STREAM", "auctions.>");
        natsStreamManagementService.createStream(natsConnection, "PROPERTIES_STREAM", "properties.>");
    }
}
