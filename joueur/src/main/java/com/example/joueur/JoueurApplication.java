package com.example.joueur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class JoueurApplication {

    public static void main(String[] args) {
        SpringApplication.run(JoueurApplication.class, args);
    }

}
