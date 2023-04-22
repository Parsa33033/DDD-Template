package org.example.test.mock.config.failure;

import org.example.framework.error.Error;
import org.example.framework.error.OperationError;
import org.example.framework.exception.StorageNotFoundException;

public enum TestError implements TestFailure {
  OTHER(Error.of(OperationError.OTHER));

  private final Error error;

  TestError(Error error) {
    this.error = error;
  }

  public Error error() {
    return this.error;
  }
}
