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
 * Globální třída pro zpracování výjimek v rámci celé aplikace.
 * *
 * Díky anotaci @ControllerAdvice se zde definované metody
 * pro zpracování výjimek automaticky aplikují na všechny controllery.
 * *
 * Tato třída je určena k zachytávání a vhodnému zpracování
 * výjimek typu EntityNotFoundException nebo podobných.
 */
@ControllerAdvice
public class EntityNotFoundExceptionAdvice {

    /**
     * Zpracovává výjimky typu NotFoundException a EntityNotFoundException.
     * *
     * Když dojde k některé z těchto výjimek, vrátí HTTP odpověď
     * s kódem 404 (Not Found) a tělem obsahujícím informace o chybě.
     *
     * @param e zachycená výjimka
     * @return ResponseEntity s objektem ApiError a HTTP stavem 404
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
