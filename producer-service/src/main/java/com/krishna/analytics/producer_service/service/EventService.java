package com.krishna.analytics.producer_service.service;

import com.krishna.analytics.producer_service.dto.EventAcceptedResponse;
import com.krishna.analytics.producer_service.dto.EventRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service // it will tell the springBoot to create the bean
public class EventService {
    public EventAcceptedResponse accept(EventRequest eventRequest) {
        String eventId = UUID.randomUUID().toString();

        return new EventAcceptedResponse(eventId , "ACCEPTED");
    }
}
