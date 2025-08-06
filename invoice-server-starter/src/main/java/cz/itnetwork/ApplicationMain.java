package cz.itnetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class with the @SpringBootApplication annotation.
 * <p>
 * Serves as the entry point for running the Spring Boot application.
 */
@SpringBootApplication
public class ApplicationMain {

    /**
     * Entry point of the application.
     * <p>
     * Starts the Spring Boot application using the run method.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);
    }
}
