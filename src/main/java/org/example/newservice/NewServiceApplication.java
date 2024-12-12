package org.example.newservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewServiceApplication.class, args);

    }

}
