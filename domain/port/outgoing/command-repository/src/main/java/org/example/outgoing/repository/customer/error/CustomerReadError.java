package org.example.outgoing.repository.customer.error;

import org.example.framework.error.Error;
import org.example.framework.error.OperationError;
import org.example.model.config.error.OperationErrorCode;

public class CustomerReadError extends OperationError {

  private static String CUSTOMER_NOT_FOUND = OperationErrorCode.CUSTOMER_NOT_FOUND;

  protected CustomerReadError(final String code) {
    super(code);
  }

  public static CustomerReadError of(String code) {
    return new CustomerReadError(code);
  }
}
