package cz.itnetwork.controller.advice;

import cz.itnetwork.dto.ApiError;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.webjars.NotFoundException;

@ControllerAdvice
public class EntityNotFoundExceptionAdvice {

    @ExceptionHandler({NotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<ApiError> handleEntityNotFoundException(RuntimeException e) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage() != null ? e.getMessage() : "Záznam nebyl nalezen"
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
