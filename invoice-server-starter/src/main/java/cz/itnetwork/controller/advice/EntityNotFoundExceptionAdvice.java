package cz.itnetwork.controller.advice;

import cz.itnetwork.dto.ApiError;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.webjars.NotFoundException;

/**
 * Global exception handler class for the entire application.
 * <p>
 * Thanks to the @ControllerAdvice annotation, the exception-handling methods
 * defined here are automatically applied to all controllers.
 * <p>
 * This class is intended to catch and handle exceptions
 * such as EntityNotFoundException or similar ones.
 */
@ControllerAdvice
public class EntityNotFoundExceptionAdvice {

    /**
     * Handles exceptions of type NotFoundException and EntityNotFoundException.
     * <p>
     * When any of these exceptions occur, it returns an HTTP response
     * with status code 404 (Not Found) and a body containing error information.
     *
     * @param e the caught exception
     * @return ResponseEntity with an ApiError object and HTTP status 404
     */
    @ExceptionHandler({NotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<ApiError> handleEntityNotFoundException(RuntimeException e) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage() != null ? e.getMessage() : "Záznam nebyl nalezen"
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
