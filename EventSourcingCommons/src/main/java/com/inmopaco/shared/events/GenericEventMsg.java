package com.inmopaco.shared.events;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Log4j2
@Getter
public abstract class GenericEventMsg implements Serializable {

    private final UUID eventId;
    protected final LocalDateTime createdAt;
    protected LocalDateTime publishedAt;
    protected LocalDateTime consumedAt;
    protected String producedBy;
    protected String consumedBy;
    protected EventStatus status;
    protected boolean isPersistent;

    protected GenericEventMsg() {
        this.eventId = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.isPersistent = true;
        this.changeStatus(EventStatus.CREATED);
    }

    public void published(LocalDateTime publishedAt) {
        changeStatus(EventStatus.PUBLISHED);
        this.publishedAt = publishedAt;
    }

    public void consumed(LocalDateTime consumedAt) {
        changeStatus(EventStatus.CONSUMED);

        this.consumedAt = consumedAt;
    }

    public void error() {
        changeStatus(EventStatus.ERROR);
    }

    public void changePersistence(boolean isPersistent) {
        this.isPersistent = isPersistent;
    }

    private void changeStatus(EventStatus newStatus){
        this.status = newStatus;
        log.info("Event {} status changed to {}", eventId, newStatus);
    }
}
