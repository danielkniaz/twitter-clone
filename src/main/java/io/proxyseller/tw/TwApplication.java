package io.proxyseller.tw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "io.proxyseller.tw")
public class TwApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwApplication.class, args);
    }

}
