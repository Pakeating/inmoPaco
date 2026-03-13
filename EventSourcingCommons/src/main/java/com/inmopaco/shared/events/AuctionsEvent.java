package com.inmopaco.shared.events;

import com.inmopaco.shared.events.enums.AuctionsActions;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class AuctionsEvent extends GenericEventMsg {
    private AuctionsActions action;
    private String payload;

    private AuctionsEvent(UUID eventId, AuctionsActions action, String payload) {
        super(eventId);
        this.action = action;
        this.payload = payload;
    }

    public static AuctionsEvent createEventMsg(AuctionsActions action, String payload) {
        return new AuctionsEvent(UUID.randomUUID(), action, payload);
    }
}
