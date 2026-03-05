package com.inmopaco.shared.events;

public class AuctionsEvent extends GenericEventMsg {

    private AuctionsEvent() {
        super();
    }

    public static AuctionsEvent createEventMsg() {
        return new AuctionsEvent();
    }
}
