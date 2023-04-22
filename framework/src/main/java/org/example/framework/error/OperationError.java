package org.example.framework.error;

public class OperationError implements Error {

  public static String OTHER = ImmutableError.OTHER.code();

  public final Error error;

  public final String code;

  protected OperationError(String code) {
    this.code = code;
    error = Error.of(code);
  }

  @Override
  public String code() {
    return this.code;
  }
}
