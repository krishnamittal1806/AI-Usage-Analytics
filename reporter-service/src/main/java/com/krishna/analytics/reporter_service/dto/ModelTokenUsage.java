package com.krishna.analytics.reporter_service.dto;
import java.time.LocalDate;

public record ModelTokenUsage(LocalDate day, String model, long responses,
                              long inputTokens, long outputTokens, double avgLatencyMs) {}