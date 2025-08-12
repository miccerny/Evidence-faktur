package cz.itnetwork.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(EmailNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleEmailNotFound(EmailNotFoundException e) {
        return Map.of(
                "error", "EMAIL_NOT_FOUND",
                "message", e.getMessage()
        );
    }

    @ExceptionHandler(BadPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleBadPassword(BadPasswordException e) {
        return Map.of(
                "error", "BAD_PASSWORD",
                "message", e.getMessage()
        );
    }
}