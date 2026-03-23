package com.inmopaco.shared.events;

import com.inmopaco.shared.events.enums.PropertiesActions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;

import java.util.UUID;

@Getter
@NoArgsConstructor
@RegisterReflectionForBinding
public class PropertiesEvent extends GenericEventMsg {
    private PropertiesActions action;
    private String payload;

    private PropertiesEvent(UUID eventId, PropertiesActions action, String payload) {
        super(eventId);
        this.action = action;
        this.payload = payload;
    }

    public static PropertiesEvent createEventMsg(PropertiesActions action, String payload) {
        return new PropertiesEvent(UUID.randomUUID(), action, payload);
    }
}
