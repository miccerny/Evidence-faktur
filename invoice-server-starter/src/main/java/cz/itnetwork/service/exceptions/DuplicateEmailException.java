package cz.itnetwork.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DuplicateEmailException extends RuntimeException {

    @ExceptionHandler({DuplicateEmailException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleEntityNotFoundException() {
    }
}
