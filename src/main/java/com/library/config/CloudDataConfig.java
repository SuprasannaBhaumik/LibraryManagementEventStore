package com.library.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.cloud.config.java.AbstractCloudConfig;

import com.library.model.DomainEventEntity;
import com.library.model.DomainEventsEntity;

import javax.sql.DataSource;

@Configuration
@EntityScan(basePackageClasses = {DomainEventsEntity.class, DomainEventEntity.class, Jsr310JpaConverters.class})
@Profile({"cloud"})
public class CloudDataConfig extends AbstractCloudConfig {

    @Bean
    public DataSource dataSource() {
        return connectionFactory().dataSource();
    }

}
