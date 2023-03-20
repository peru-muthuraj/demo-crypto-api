package com.example.crypto.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CryptoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CryptoApplication.class, args);
    }

}
