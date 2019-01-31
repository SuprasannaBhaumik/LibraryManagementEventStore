package com.library.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

import java.util.List;


@Data
public class DomainEvents {

    private String userId;

    @JsonRawValue
    private List<String> domainEvents;

}
