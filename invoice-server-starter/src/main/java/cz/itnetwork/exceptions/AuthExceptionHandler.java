package cz.itnetwork.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

/**
 * Global exception handler for authentication-related errors.
 * <p>
 * This class is annotated with {@link RestControllerAdvice} to catch and handle
 * specific exceptions thrown during authentication and return consistent HTTP responses.
 */
@RestControllerAdvice
public class AuthExceptionHandler {

    /**
     * Handles cases where the email address provided during login is not found.
     * <p>
     * Returns an HTTP 401 (Unauthorized) status along with a structured JSON error response.
     *
     * @param e the {@link EmailNotFoundException} thrown when no user exists with the given email
     * @return a map containing an error code and descriptive message
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
     * Handles cases where the provided password does not match the stored password.
     * <p>
     * Returns an HTTP 401 (Unauthorized) status along with a structured JSON error response.
     *
     * @param e the {@link BadPasswordException} thrown when the supplied password is incorrect
     * @return a map containing an error code and descriptive message
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