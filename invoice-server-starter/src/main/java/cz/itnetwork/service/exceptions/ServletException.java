package cz.itnetwork.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ServletException extends RuntimeException {
  @ExceptionHandler(ServletException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public void handleServletException() {
  }
}
