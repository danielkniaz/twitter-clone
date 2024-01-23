package com.maup.network;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.maup.network")
public class TwApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwApplication.class, args);
    }

}
