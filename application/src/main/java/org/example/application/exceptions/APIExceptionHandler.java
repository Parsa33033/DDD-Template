package org.example.application.exceptions;

import java.time.ZonedDateTime;
import javax.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {

  private final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
  private final HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity handleValidationException(ValidationException e) {
    APIExceptionResult exception = new APIExceptionResult(
        e.getMessage(),
        BAD_REQUEST,
        ZonedDateTime.now());
    return new ResponseEntity<>(exception, BAD_REQUEST);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity handleFunctionalError(RuntimeException e) {
    APIExceptionResult exception = new APIExceptionResult(
        e.getMessage(),
        INTERNAL_SERVER_ERROR,
        ZonedDateTime.now());
    return new ResponseEntity<>(exception, INTERNAL_SERVER_ERROR);
  }
}
