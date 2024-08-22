package com.erp.gameserver.autoconfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration(proxyBeanMethods = false)
public class ExecutorAutoConfiguration {

    @Bean
    public ExecutorService bizExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

}
