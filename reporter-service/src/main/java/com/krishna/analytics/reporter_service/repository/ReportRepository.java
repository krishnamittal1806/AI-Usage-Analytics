package com.krishna.analytics.reporter_service.repository;

import com.krishna.analytics.reporter_service.dto.DailyTypeCount;
import com.krishna.analytics.reporter_service.dto.ModelTokenUsage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ReportRepository {
    private final NamedParameterJdbcTemplate jdbc;

    public long countEvents(OffsetDateTime from, OffsetDateTime to) {
        Long n = jdbc.queryForObject("""
            SELECT COUNT(*) FROM analytics_events
            WHERE event_time >= :from AND event_time < :to
            """, Map.of("from", from, "to", to), Long.class);
        return n == null ? 0 : n;
    }

    public List<DailyTypeCount> countByTypePerDay(OffsetDateTime from, OffsetDateTime to) {
        return jdbc.query("""
            SELECT (event_time AT TIME ZONE 'UTC')::date AS day, event_type, COUNT(*) AS cnt
            FROM analytics_events
            WHERE event_time >= :from AND event_time < :to
            GROUP BY 1, 2 ORDER BY 1, 2
            """, Map.of("from", from, "to", to),
                (rs, i) -> new DailyTypeCount(rs.getObject("day", LocalDate.class),
                        rs.getString("event_type"), rs.getLong("cnt")));
    }

    public List<ModelTokenUsage> tokenUsagePerModelPerDay(OffsetDateTime from, OffsetDateTime to) {
        return jdbc.query("""
            SELECT (event_time AT TIME ZONE 'UTC')::date AS day,
                   metadata->>'model' AS model,
                   COUNT(*) AS responses,
                   SUM((metadata->>'inputTokens')::bigint)  AS input_tokens,
                   SUM((metadata->>'outputTokens')::bigint) AS output_tokens,
                   ROUND(AVG((metadata->>'latencyMs')::numeric), 1) AS avg_latency_ms
            FROM analytics_events
            WHERE event_type = 'AI_RESPONSE'
              AND event_time >= :from AND event_time < :to
            GROUP BY 1, 2 ORDER BY 1, 2
            """, Map.of("from", from, "to", to),
                (rs, i) -> new ModelTokenUsage(rs.getObject("day", LocalDate.class),
                        rs.getString("model"), rs.getLong("responses"),
                        rs.getLong("input_tokens"), rs.getLong("output_tokens"),
                        rs.getDouble("avg_latency_ms")));
    }
}