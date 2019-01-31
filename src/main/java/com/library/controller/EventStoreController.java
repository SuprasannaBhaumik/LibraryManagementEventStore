package com.library.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.tuple.Tuple;
import org.springframework.tuple.TupleBuilder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.service.DomainEventService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class EventStoreController {

	private final DomainEventService service;

    public EventStoreController( final DomainEventService service ) {
        this.service = service;
    }
	
	@PostMapping("/createUser")
	public ResponseEntity saveEvent(@RequestBody String json) {
		log.info("inside saveEvent -> called from another microservice");
        Tuple event = TupleBuilder.fromString(json);
        Assert.isTrue(event.hasFieldName("eventType"), "eventType is required");
        Assert.isTrue(event.hasFieldName("userId"), "userId is required");
        Assert.isTrue(event.hasFieldName("occurredOn"), "occurredOn is required");
        this.service.processDomainEvent(event);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity domainEvents(@PathVariable("userId") UUID userId) {
    	log.info("inside domainEvents -> called from another microservice");
        return ResponseEntity
                .ok(this.service.getDomainEvents(userId.toString()));
    }
	

}
