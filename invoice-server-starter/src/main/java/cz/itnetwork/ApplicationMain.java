package cz.itnetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class annotated with {@link SpringBootApplication}.
 * <p>
 * Serves as the entry point for launching the Spring Boot application.
 */
@SpringBootApplication
public class ApplicationMain {

    /**
     * Application entry point.
     * <p>
     * Starts the Spring Boot application using the {@link SpringApplication#run(Class, String...)} method.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);
    }
}

