package com.krishna.analytics.producer_service.controller;

import com.krishna.analytics.producer_service.dto.EventAcceptedResponse;
import com.krishna.analytics.producer_service.dto.EventRequest;
import com.krishna.analytics.producer_service.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/events")
@RequiredArgsConstructor // this will automatically create getters and setter
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventAcceptedResponse> create(@RequestBody EventRequest request){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(eventService.accept(request));
    }
}
