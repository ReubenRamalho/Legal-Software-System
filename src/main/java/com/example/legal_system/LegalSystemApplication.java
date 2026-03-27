package com.example.legal_system;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.legal_system.view.MenuCLIView;

/**
 * Entry point for the Legal System Spring Boot application.
 *
 * <p>Bootstraps the Spring context and launches the interactive CLI menu
 * via {@link MenuCLIView#start()} as soon as the application is ready.</p>
 */
@SpringBootApplication
public class LegalSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LegalSystemApplication.class, args);
    }

    /**
     * Registers a {@link CommandLineRunner} that starts the CLI menu after
     * the application context has been fully initialized.
     *
     * @param menuCLIView the CLI view bean injected by Spring.
     * @return a {@link CommandLineRunner} that delegates to {@link MenuCLIView#start()}.
     */
    @Bean
    public CommandLineRunner run(MenuCLIView menuCLIView) {
        return args -> {
            menuCLIView.start();
        };
    }
}
