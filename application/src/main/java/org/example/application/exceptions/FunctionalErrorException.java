package org.example.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class FunctionalErrorException extends RuntimeException {

  public FunctionalErrorException(String message) {
    super("Functional error happened with message:" + message);
  }
}
