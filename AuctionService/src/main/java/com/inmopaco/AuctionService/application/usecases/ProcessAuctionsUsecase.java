package com.inmopaco.AuctionService.application.usecases;

import com.inmopaco.shared.events.AuctionsEvent;

public interface ProcessAuctionsUsecase {

    void processAuctions(AuctionsEvent event);
}
