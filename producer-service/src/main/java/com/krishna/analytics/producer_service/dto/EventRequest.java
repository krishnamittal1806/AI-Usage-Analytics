package com.krishna.analytics.producer_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record EventRequest(
        UUID eventId,
        @NotNull EventType eventType,
        @NotBlank @Size(max = 64) String userId,
        Instant timestamp,
        @NotNull Map<String, Object> metadata
) {
}
