package com.krishna.analytics.reporter_service.controller;

import com.krishna.analytics.reporter_service.dto.DailyTypeCount;
import com.krishna.analytics.reporter_service.dto.ModelTokenUsage;
import com.krishna.analytics.reporter_service.dto.TotalEventsReport;
import com.krishna.analytics.reporter_service.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService service;

    @GetMapping("/summary")
    public TotalEventsReport summary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to) {
        return service.summary(from, to);
    }

    @GetMapping("/events-by-type-daily")
    public List<DailyTypeCount> byTypeDaily(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to) {
        return service.byTypePerDay(from, to);
    }

    @GetMapping("/token-usage")
    public List<ModelTokenUsage> tokenUsage(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to) {
        return service.tokenUsage(from, to);
    }
}