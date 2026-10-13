package com.krishna.analytics.producer_service.publisher;

import com.krishna.analytics.producer_service.dto.AnalyticsEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final JsonMapper jsonMapper;          // tools.jackson.databind.json.JsonMapper
    @Value("${app.kafka.topic}") private String topic;

    public void publish(AnalyticsEvent event) {
        String payload = jsonMapper.writeValueAsString(event);
        kafkaTemplate.send(topic, event.userId(), payload)
                .whenComplete((result, ex) -> {
                    if (ex != null) log.error("Publish failed eventId={}", event.eventId(), ex);
                    else log.info("Published eventId={} partition={} offset={}",
                            event.eventId(), result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset());
                });
    }
}