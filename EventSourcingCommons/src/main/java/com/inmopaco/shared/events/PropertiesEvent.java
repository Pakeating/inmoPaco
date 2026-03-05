package com.inmopaco.shared.events;

public class PropertiesEvent extends GenericEventMsg {

    private PropertiesEvent() {
        super();
    }

    public static PropertiesEvent createEventMsg() {
        return new PropertiesEvent();
    }
}
