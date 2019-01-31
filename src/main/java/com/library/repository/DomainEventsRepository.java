package com.library.repository;

import org.springframework.data.repository.CrudRepository;

import com.library.model.DomainEventsEntity;

public interface DomainEventsRepository extends CrudRepository<DomainEventsEntity, String> {

}
