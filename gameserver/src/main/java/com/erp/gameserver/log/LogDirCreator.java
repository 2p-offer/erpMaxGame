package com.erp.gameserver.log;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class LogDirCreator implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Initializing log directory...");

        String logDirectoryPath = "/Users/erp/tmp/log4j2/gc/";

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
