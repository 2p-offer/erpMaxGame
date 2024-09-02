package com.erp.core.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class EventManagerInitializer {
    @Bean
    public EventManager setApplicationContext(@Nonnull ApplicationContext applicationContext) {
        return new EventManager(applicationContext);
    }
}
