package com.example.centralservers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class CentralserversApplication {

    public static void main(String[] args) {
        SpringApplication.run(CentralserversApplication.class, args);
    }

}
