package com.inmopaco.Orchestrator.infrastructure.queues.provider.nats.config;

import io.nats.client.Connection;
import io.nats.client.JetStreamManagement;
import io.nats.client.api.RetentionPolicy;
import io.nats.client.api.StorageType;
import io.nats.client.api.StreamConfiguration;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NatsStreamConfig {

    private final Connection natsConnection;

    //Configuracion para crear streams de NATS para persistencia de msg
    @PostConstruct
    public void createStream() throws Exception {
        JetStreamManagement jsm = natsConnection.jetStreamManagement();

        StreamConfiguration streamConfig = StreamConfiguration.builder()
                .name("AUCTIONS_STREAM") // nombre del contenedor
                .subjects("auctions.>")    // se guardan subjects que empiecen por auctions
                .storageType(StorageType.File) // guardado a disco
                .retentionPolicy(RetentionPolicy.Limits)
                .build();

        try {
            jsm.getStreamInfo("AUCTIONS_STREAM");
        } catch (Exception e) {
            jsm.addStream(streamConfig);
        }
    }
}
