package cz.itnetwork.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

/**
 * Global exception handler for authentication errors.
 * <p>
 * Handles custom exceptions related to login and authentication,
 * such as when an email is not found or the password is incorrect.
 * Returns a simple JSON error response and sets the HTTP status to 401 (Unauthorized).
 */
@RestControllerAdvice
public class AuthExceptionHandler {

    /**
     * Handles EmailNotFoundException when a user with the given email does not exist.
     * Responds with HTTP 401 Unauthorized and a simple error message.
     *
     * @param e the thrown EmailNotFoundException
     * @return a map with error type and message
     */
    @ExceptionHandler(EmailNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleEmailNotFound(EmailNotFoundException e) {
        return Map.of(
                "error", "EMAIL_NOT_FOUND",
                "message", e.getMessage()
        );
    }

    /**
     * Handles BadPasswordException when the password is incorrect.
     * Responds with HTTP 401 Unauthorized and a simple error message.
     *
     * @param e the thrown BadPasswordException
     * @return a map with error type and message
     */
    @ExceptionHandler(BadPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleBadPassword(BadPasswordException e) {
        return Map.of(
                "error", "BAD_PASSWORD",
                "message", e.getMessage()
        );
    }
}