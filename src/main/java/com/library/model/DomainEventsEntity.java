package com.library.model;

import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.LinkedHashSet;
import java.util.Set;
import static java.util.stream.Collectors.toList;
import static javax.persistence.CascadeType.ALL;

@Entity(name="domainEvents")
@Table(name="LIB_DOMAIN_EVENTS")
@Data
@EqualsAndHashCode(exclude={"domainEvents"})
public class DomainEventsEntity {

    @Id
    private String userId;

    @OneToMany(cascade=ALL)
    @JoinColumn(name = "USER_ID")
    @OrderBy("occurredOn ASC")
    private Set<DomainEventEntity> domainEvents;

    public DomainEventsEntity() {
        this.domainEvents = new LinkedHashSet<>();
    }

    public DomainEventsEntity(final String userId) {
        this();
        this.userId = userId;
    }

    public DomainEvents toModel() {
        DomainEvents model = new DomainEvents();
        model.setUserId(userId);
        model.setDomainEvents(
        		domainEvents.stream()
                			.map(DomainEventEntity::getData)
                			.collect(toList()));
        return model;
    }

}