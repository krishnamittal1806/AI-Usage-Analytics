package com.krishna.analytics.producer_service.dto;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record AnalyticsEvent(
        UUID eventId,
        EventType eventType,
        String userId,
        Instant eventTime,
        Instant receivedAt,
        Map<String, Object> metadata
) {
}
