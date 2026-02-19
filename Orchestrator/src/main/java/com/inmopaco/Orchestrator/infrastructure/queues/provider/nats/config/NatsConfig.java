package com.inmopaco.Orchestrator.infrastructure.queues.provider.nats.config;

import io.nats.client.Connection;
import io.nats.client.JetStreamManagement;
import io.nats.client.Nats;
import io.nats.client.Options;
import io.nats.client.api.RetentionPolicy;
import io.nats.client.api.StorageType;
import io.nats.client.api.StreamConfiguration;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class NatsConfig {

    //TODO: Sacar de configService y de yml
    private final String natsUrl = "localhost:4222";
    private final Connection natsConnection;

    @Bean(destroyMethod = "close")
    public Connection natsConnection() throws Exception {
        Options options = new Options.Builder()
                .server(natsUrl)
                .maxReconnects(-1) // Reintentos infinitos
                .build();
        return Nats.connect(options);
    }

    //Configuracion para crear streams de NATS para persistencia de msg
    //TODO: se hace solo uno, pero tiene que conectarse a todos los que haya en un fichero de configuracion o algo
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
