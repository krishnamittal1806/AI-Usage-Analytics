package com.krishna.analytics.producer_service.service;

import com.krishna.analytics.producer_service.dto.EventType;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class MetadataValidator {
    private static final Map<EventType, Set<String>> REQUIRED = Map.of(
            EventType.AI_REQUEST, Set.of("requestId", "model"),
            EventType.AI_RESPONSE, Set.of("requestId", "model", "inputTokens", "outputTokens", "latencyMs"),
            EventType.AI_ERROR, Set.of("requestId", "model", "errorType"),
            EventType.USER_FEEDBACK, Set.of("requestId", "rating")
    );

    public void validate(EventType type, Map<String, Object> metadata) {
        if (type == null) {
            throw new InvalidEventException("eventType is required");
        }
        if (metadata == null) {
            throw new InvalidEventException("metadata is required");
        }

        Set<String> missing = new HashSet<>(REQUIRED.get(type));
        missing.removeAll(metadata.keySet());
        if (!missing.isEmpty()) {
            throw new InvalidEventException("Missing metadata for " + type + ": " + missing);
        }
    }
}
