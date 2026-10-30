package com.krishna.analytics.consumer.service;

import com.krishna.analytics.consumer.entity.EventEntity;
import com.krishna.analytics.consumer.event.AnalyticsEvent;
import com.krishna.analytics.consumer.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.json.JsonMapper;

@Service
@RequiredArgsConstructor
public class EventStoreService {
    private final EventRepository repository;
    private final JsonMapper jsonMapper;

    @Transactional
    public void store(AnalyticsEvent e) {
        repository.save(new EventEntity(e.eventId(), e.eventType(), e.userId(),
                e.eventTime(), e.receivedAt(), jsonMapper.writeValueAsString(e.metadata())));
    }
}