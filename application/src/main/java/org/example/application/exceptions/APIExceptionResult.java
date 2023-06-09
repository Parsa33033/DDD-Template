package org.example.application.exceptions;

import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;

public class APIExceptionResult {


  private final String message;

  private final HttpStatus httpStatus;

  private final ZonedDateTime zonedDateTime;

  public APIExceptionResult(
      String message,
      HttpStatus httpStatus,
      ZonedDateTime zonedDateTime) {
    this.message = message;
    this.httpStatus = httpStatus;
    this.zonedDateTime = zonedDateTime;
  }

  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public ZonedDateTime getZonedDateTime() {
    return zonedDateTime;
  }

}
