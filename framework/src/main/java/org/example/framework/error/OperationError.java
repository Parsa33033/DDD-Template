package org.example.framework.error;

import static org.example.framework.error.ImmutableError.tryGetMessageForCode;

import org.example.framework.DomainConfig;

public class OperationError implements Error {

  public static String OTHER = ImmutableError.OTHER.code();

  public final Error error;

  public final String code;
  public String message;

  protected OperationError(String code) {
    this.code = code;
    this.error = Error.of(code);
    this.message = tryGetMessageForCode(code);
  }

  protected OperationError(String code, String message) {
    this(code);
    this.message = message;
  }

  @Override
  public String code() {
    return this.code;
  }

  @Override
  public String message() {
    return this.message;
  }

}
