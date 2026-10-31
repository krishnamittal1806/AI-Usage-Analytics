package com.krishna.analytics.reporter_service.dto;

import java.time.LocalDate;
public record DailyTypeCount(LocalDate day, String eventType, long count) {}