package com.library.service;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.Publisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.tuple.Tuple;
import org.springframework.tuple.TupleToJsonStringConverter;

import lombok.extern.slf4j.Slf4j;

@EnableBinding(Source.class)
@Slf4j
public class NotificationPublisherImpl implements NotificationPublisher {

    private final TupleToJsonStringConverter converter;

    public NotificationPublisherImpl(final TupleToJsonStringConverter tupleToJsonStringConverter) {
        this.converter = tupleToJsonStringConverter;
    }

    @Publisher(channel=Source.OUTPUT )
    public Message<String> sendNotification(Tuple event) {
        String payload = converter.convert(event);
        log.info("payload json :"+payload);
        return MessageBuilder
                .withPayload(payload)
                .setHeader( "x-delay", 1000 )
                .build();
    }

}
