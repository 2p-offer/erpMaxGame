package com.erp.logicserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.erp")
@SpringBootApplication
public class LogicServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogicServerApplication.class, args);
    }
}
