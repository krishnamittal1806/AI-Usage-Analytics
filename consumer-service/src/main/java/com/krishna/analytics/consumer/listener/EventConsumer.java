package com.krishna.analytics.consumer.listener;

import com.krishna.analytics.consumer.event.AnalyticsEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;
import com.krishna.analytics.consumer.service.EventStoreService;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventConsumer {
    private final JsonMapper jsonMapper;
    private final EventStoreService storeService;   // add

    @KafkaListener(topics = "${app.kafka.topic}")
    public void consume(ConsumerRecord<String, String> record) {
        AnalyticsEvent event = jsonMapper.readValue(record.value(), AnalyticsEvent.class);
        storeService.store(event);                  // add
        log.info("Stored eventId={} type={} user={} partition={} offset={}",
                event.eventId(), event.eventType(), event.userId(), record.partition(), record.offset());
    }
}
