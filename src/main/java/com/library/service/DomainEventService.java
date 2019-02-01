package com.library.service;

import org.springframework.tuple.Tuple;

import com.library.model.DomainEventEntity;
import com.library.model.DomainEvents;
import com.library.model.DomainEventsEntity;
import com.library.repository.DomainEventsRepository;
import com.library.util.JsonStringToTupleConverter;
import com.library.util.TupleToJsonStringConverter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Slf4j
@Transactional(readOnly=true)
public class DomainEventService {

	private final DomainEventsRepository domainEventsRepository;
    private final NotificationPublisher publisher;
    private final TupleToJsonStringConverter toJsonStringConverter;
    private final JsonStringToTupleConverter toTupleConverter;


    public DomainEventService(
            final DomainEventsRepository domainEventsRepository, final NotificationPublisher publisher,
            final TupleToJsonStringConverter toJsonStringConverter, final JsonStringToTupleConverter toTupleConverter) {

        this.domainEventsRepository = domainEventsRepository;
        this.publisher = publisher;
        this.toJsonStringConverter = toJsonStringConverter;
        this.toTupleConverter = toTupleConverter;

    }

    public DomainEvents getDomainEvents(final String userId) {
        log.debug( "processDomainEvent : enter" );
        log.debug( "processDomainEvent : userId=" + userId );

        return domainEventsRepository.findById(userId)
                .map(DomainEventsEntity::toModel)
                .orElseThrow(IllegalArgumentException::new );
    }

    @Transactional
    public void processDomainEvent(final Tuple event) {
    	
        log.debug("processDomainEvent : enter");
        log.debug("processDomainEvent : event[{}] ", event);

        String eventType = event.getString("eventType");
        
        switch (eventType) {
            case "UserInitialized":
                processUserInitialized(event);
                break;
            default:
                processUserEvent(event);
                break;
        }
        
        log.debug( "processDomainEvent : calling publisher.sendNotification(event)" );
        //sending the message to kafka queue, which will be consumed by the listeners
        this.publisher.sendNotification(event);
        log.debug( "processDomainEvent : exit" );
    }

    private void processUserInitialized(final Tuple event) {
        log.info("processUserInitialized : enter");

        String userId = event.getString( "userId");
        String occuredOnString = event.getString("occurredOn");
        log.info(occuredOnString);
        Instant occurredOn = Instant.parse(occuredOnString);

        DomainEventsEntity domainEventsEntity = new DomainEventsEntity(userId);
        DomainEventEntity domainEventEntity = new DomainEventEntity();

        domainEventEntity.setId(UUID.randomUUID().toString());
        domainEventEntity.setOccurredOn(LocalDateTime.ofInstant(occurredOn, ZoneOffset.UTC));
        domainEventEntity.setData(this.toJsonStringConverter.convert(event) );

        domainEventsEntity.getDomainEvents().add(domainEventEntity);

        this.domainEventsRepository.save(domainEventsEntity);
    }

    private void processUserEvent( final Tuple event ) {
    	
        log.debug( "processUserEvent : enter " );
        String userId = event.getString("userId");

        this.domainEventsRepository.findById(userId)
                .ifPresent( found -> {
                    log.debug( "processUserEvent : a DomainEventsEntity[{}] was found for userId[{}]. ", found, userId );

                    DomainEventEntity domainEventEntity = new DomainEventEntity();
                    domainEventEntity.setId( UUID.randomUUID().toString() );

                    Instant occurredOn = Instant.parse( event.getString( "occurredOn" ) );
                    domainEventEntity.setOccurredOn( LocalDateTime.ofInstant( occurredOn, ZoneOffset.UTC ) );

                    domainEventEntity.setData( toJsonStringConverter.convert( event ) );

                    found.getDomainEvents().add( domainEventEntity );
                    
                    this.domainEventsRepository.save( found );

                });

    }

}


