package com.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.library.repository.DomainEventsRepository;
import com.library.service.DomainEventService;
import com.library.service.NotificationPublisher;
import com.library.util.JsonStringToTupleConverter;
import com.library.util.TupleToJsonStringConverter;

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
