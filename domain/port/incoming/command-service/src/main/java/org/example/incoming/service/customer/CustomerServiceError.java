package org.example.incoming.service.customer;

import org.example.framework.error.Error;
import org.example.framework.error.OperationError;
import org.example.model.config.error.OperationErrorCode;

public class CustomerServiceError extends OperationError {

  public static String CUSTOMER_EXISTS = OperationErrorCode.CUSTOMER_EXISTS;

  protected CustomerServiceError(final String code) {
    super(code);
  }

  public static CustomerServiceError of(String code) {
    return new CustomerServiceError(code);
  }
}
