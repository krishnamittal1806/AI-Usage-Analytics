package com.krishna.analytics.reporter_service.dto;

import java.time.Instant;
public record TotalEventsReport(Instant from, Instant to, long totalEvents) {

}