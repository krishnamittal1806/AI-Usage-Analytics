package com.krishna.analytics.reporter_service.service;

import com.krishna.analytics.reporter_service.dto.DailyTypeCount;
import com.krishna.analytics.reporter_service.dto.ModelTokenUsage;
import com.krishna.analytics.reporter_service.dto.TotalEventsReport;
import com.krishna.analytics.reporter_service.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository repo;

    public TotalEventsReport summary(Instant from, Instant to) {
        check(from, to);
        return new TotalEventsReport(from, to, repo.countEvents(utc(from), utc(to)));
    }
    public List<DailyTypeCount> byTypePerDay(Instant from, Instant to) {
        check(from, to);
        return repo.countByTypePerDay(utc(from), utc(to));
    }
    public List<ModelTokenUsage> tokenUsage(Instant from, Instant to) {
        check(from, to);
        return repo.tokenUsagePerModelPerDay(utc(from), utc(to));
    }

    private void check(Instant from, Instant to) {
        if (!from.isBefore(to))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'from' must be before 'to'");
    }
    private OffsetDateTime utc(Instant i) { return OffsetDateTime.ofInstant(i, ZoneOffset.UTC); }
}