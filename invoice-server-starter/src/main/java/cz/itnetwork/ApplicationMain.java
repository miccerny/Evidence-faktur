package cz.itnetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hlavní třída aplikace s anotací SpringBootApplication.
 * *
 * Slouží jako vstupní bod pro spuštění Spring Boot aplikace.
 */
@SpringBootApplication
public class ApplicationMain {

    /**
     * Vstupní bod aplikace.
     * *
     * Spustí Spring Boot aplikaci pomocí metody run.
     *
     * @param args argumenty příkazové řádky
     */
    public static void main(String[] args) {

        SpringApplication.run(ApplicationMain.class, args);
    }
}
