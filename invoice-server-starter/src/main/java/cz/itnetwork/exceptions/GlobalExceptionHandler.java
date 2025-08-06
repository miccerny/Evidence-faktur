package cz.itnetwork.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the application.
 * <p>
 * Handles exceptions that can occur anywhere in the application,
 * such as when a user tries to register with an email address that is already in use.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles DuplicateEmailException.
     * <p>
     * Returns a plain text response with HTTP status 400 (Bad Request)
     * and includes the error message.
     *
     * @param e the thrown DuplicateEmailException
     * @return a ResponseEntity with the error message and status 400
     */
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<String> handleDuplicateEmail(DuplicateEmailException e) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("text/plain; charset=UTF-8"));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .headers(httpHeaders)
                .body(e.getMessage());
    }
}
