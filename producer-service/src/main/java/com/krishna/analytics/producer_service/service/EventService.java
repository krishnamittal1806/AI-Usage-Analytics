package com.krishna.analytics.producer_service.service;

import com.krishna.analytics.producer_service.dto.EventAcceptedResponse;
import com.krishna.analytics.producer_service.dto.AnalyticsEvent;
import com.krishna.analytics.producer_service.dto.EventRequest;
import com.krishna.analytics.producer_service.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventPublisher eventPublisher;
    private final MetadataValidator validator;

    public EventAcceptedResponse accept(EventRequest eventRequest) {
        validator.validate(eventRequest.eventType(), eventRequest.metadata());
        Instant receivedAt = Instant.now();
        AnalyticsEvent event = new AnalyticsEvent(
                eventRequest.eventId() != null ? eventRequest.eventId() : UUID.randomUUID(),
                eventRequest.eventType(),
                eventRequest.userId(),
                eventRequest.timestamp() != null ? eventRequest.timestamp() : receivedAt,
                receivedAt,
                Collections.unmodifiableMap(new HashMap<>(eventRequest.metadata()))
        );

        eventPublisher.publish(event);
        return new EventAcceptedResponse(event.eventId().toString(), "ACCEPTED");
    }
}
