package com.inmopaco.Orchestrator.infrastructure.schedulers.impl;

import com.inmopaco.Orchestrator.application.usecase.AuctionsUsecaseService;
import com.inmopaco.Orchestrator.infrastructure.schedulers.AuctionSchedulers;
import com.inmopaco.Orchestrator.infrastructure.schedulers.config.AuctionConfig;
import com.inmopaco.shared.events.AuctionsEvent;
import com.inmopaco.shared.events.enums.AuctionsActions;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AuctionSchedulersImpl implements AuctionSchedulers {

    @Autowired
    AuctionConfig auctionConfig;
    @Autowired
    AuctionsUsecaseService auctionsUsecaseService;

    @Scheduled(cron = "${schedules.auctions.get-auctions.cron:}")
    @Override
    public void scrapeBoeAuctions() {
        auctionConfig.getProvinces().forEach((province, code) -> {
            log.info("[AuctionSchedulersImpl] Triggering scrape for province: {} with code: {}", province, code);
            auctionsUsecaseService.getAuctions(AuctionsEvent.createEventMsg(AuctionsActions.GET_AUCTIONS, code));
        });
        log.info("Events generated for {} provinces",auctionConfig.getProvinces().size());
    }
}
