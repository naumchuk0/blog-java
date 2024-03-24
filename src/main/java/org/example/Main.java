package org.example;


import com.github.javafaker.Faker;
import org.example.services.GenerateDataFaker;
import org.example.services.StorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Locale;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    @Bean
    CommandLineRunner runner(GenerateDataFaker init) {
        return args -> {
            init.SeedAllTables();
        };
    }
}