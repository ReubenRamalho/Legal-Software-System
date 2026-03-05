package com.example.legal_system;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.legal_system.view.MenuCLIView;

@SpringBootApplication
public class LegalSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LegalSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(MenuCLIView menuCLIView) {
        return args -> {
            menuCLIView.start();
        };
    }
}
