package com.library.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name="domainEvent")
@Table(name="LIB_DOMAIN_EVENT")
@Data
@EqualsAndHashCode(exclude={"id", "occurredOn"})
@JsonIgnoreProperties(ignoreUnknown=true)
public class DomainEventEntity {

    @Id
    private String id;

    @Column(columnDefinition="TIMESTAMP")
    private LocalDateTime occurredOn;

    @Lob
    private String data;

    @Column(name="USER_ID")
    private String userId;

}
