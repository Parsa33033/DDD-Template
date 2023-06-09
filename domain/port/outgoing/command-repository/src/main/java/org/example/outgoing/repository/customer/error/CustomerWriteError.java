package org.example.outgoing.repository.customer.error;

import org.example.framework.error.Error;
import org.example.framework.error.OperationError;
import org.example.model.config.error.OperationErrorCode;

public class CustomerWriteError extends OperationError {

  public static String CUSTOMER_EXISTS = OperationErrorCode.CUSTOMER_EXISTS;
  public static String INVALID_REQUEST = OperationErrorCode.INVALID_REQUEST;

  protected CustomerWriteError(final String code) {
    super(code);
  }

  public static CustomerWriteError of(String code) {
    return new CustomerWriteError(code);
  }
}
