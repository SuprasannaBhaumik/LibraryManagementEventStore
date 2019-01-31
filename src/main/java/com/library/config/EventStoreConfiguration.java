package com.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.tuple.JsonStringToTupleConverter;
import org.springframework.tuple.TupleToJsonStringConverter;

import com.library.repository.DomainEventsRepository;
import com.library.service.DomainEventService;
import com.library.service.NotificationPublisher;

@Configuration
public class EventStoreConfiguration {

	 @Bean
	 public DomainEventService domainEventService(
			 final DomainEventsRepository domainEventsRepository, final NotificationPublisher publisher,
	         final TupleToJsonStringConverter tupleToJsonStringConverter, final JsonStringToTupleConverter jsonStringToTupleConverter
	  ){
		 return new DomainEventService( domainEventsRepository, publisher, tupleToJsonStringConverter, jsonStringToTupleConverter );
	  }
}
