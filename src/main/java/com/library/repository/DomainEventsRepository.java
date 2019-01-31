package com.library.repository;

import org.springframework.data.repository.CrudRepository;

public interface DomainEventsRepository extends CrudRepository<DomainEventsEntity, String> {

}
