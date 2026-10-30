package com.krishna.analytics.consumer.entity;

import com.krishna.analytics.consumer.event.EventType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "analytics_events")
@Getter
@NoArgsConstructor
public class EventEntity {

    @Id
    @Column(name = "event_id")
    private UUID eventId;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventType eventType;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "event_time")
    private Instant eventTime;

    @Column(name = "received_at")
    private Instant receivedAt;

    @ColumnTransformer(write = "?::jsonb")
    @Column(columnDefinition = "jsonb")
    private String metadata;

    public EventEntity(UUID eventId, EventType eventType, String userId,
                       Instant eventTime, Instant receivedAt, String metadata) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.userId = userId;
        this.eventTime = eventTime;
        this.receivedAt = receivedAt;
        this.metadata = metadata;
    }
}