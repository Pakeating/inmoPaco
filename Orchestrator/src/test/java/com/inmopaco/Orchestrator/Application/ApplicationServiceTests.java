package com.inmopaco.Orchestrator.Application;

import com.inmopaco.Orchestrator.application.usecase.AuctionsUsecaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationServiceTests {
    @Autowired
    AuctionsUsecaseService auctionsUsecaseService;

    @Autowired


    @Test
    void sendAuctionsMsg(){
        auctionsUsecaseService.GetAuctions();
    }

}
