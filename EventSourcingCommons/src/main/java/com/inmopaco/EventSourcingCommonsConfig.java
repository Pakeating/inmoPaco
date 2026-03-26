package com.inmopaco;

import com.inmopaco.shared.events.AuctionsEvent;
import com.inmopaco.shared.events.GenericEventMsg;
import com.inmopaco.shared.events.PropertiesEvent;
import com.inmopaco.shared.events.enums.AuctionsActions;
import com.inmopaco.shared.events.enums.PropertiesActions;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Configuration;


/// agregar aqui los eventos e importar esta clase en los servicios para registrar la reflexion
@Configuration
@RegisterReflectionForBinding({
        AuctionsEvent.class,
        PropertiesEvent.class,
        GenericEventMsg.class,
        AuctionsActions.class,
        PropertiesActions.class
})
public class EventSourcingCommonsConfig {}
