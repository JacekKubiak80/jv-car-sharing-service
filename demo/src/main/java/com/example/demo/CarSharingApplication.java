package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarSharingApplication {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public static void main(String[] args) {
        SpringApplication.run(CarSharingApplication.class, args);
    }

    @PostConstruct
    public void testSecret() {
        System.out.println("JWT Secret: " + jwtSecret);
    }
}
