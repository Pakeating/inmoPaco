package com.inmopaco.Orchestrator.domain.events;

public class GetAuctionsEvent extends GenericEventMsg {

    private GetAuctionsEvent() {
        super();
    }

    public static GetAuctionsEvent createEventMsg() {
        return new GetAuctionsEvent();
    }
}
