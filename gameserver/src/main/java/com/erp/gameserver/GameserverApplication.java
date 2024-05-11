package com.erp.gameserver;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@ComponentScan(basePackages = "com.erp")
@SpringBootApplication
public class GameserverApplication {

    public static void main(String[] args) {
        LogManager.getLogger(GameserverApplication.class).info("11122222222");
        SpringApplication.run(GameserverApplication.class, args);
    }

    @PostConstruct
    public void initializeLogDirectory() {
        String logDirectoryPath = "/Users/erp/tmp/log4j2/gameserver/gc/";

        Path path = Paths.get(logDirectoryPath);

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("Log directory created: " + path);
            } catch (Exception e) {
                System.err.println("Failed to create log directory: " + path);
                e.printStackTrace();
            }
        }
    }
}
