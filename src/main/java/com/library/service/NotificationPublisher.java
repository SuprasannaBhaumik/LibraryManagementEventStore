package com.library.service;

import org.springframework.messaging.Message;
import org.springframework.tuple.Tuple;

public interface NotificationPublisher {

    Message<String> sendNotification( final Tuple event );

}
