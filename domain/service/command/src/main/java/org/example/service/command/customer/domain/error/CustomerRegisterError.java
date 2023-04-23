package org.example.service.command.customer.domain.error;

import org.example.framework.error.OperationError;
import org.example.model.config.error.OperationErrorCode;

public class CustomerRegisterError extends OperationError {

  public static String CUSTOMER_ALREADY_EXISTS = OperationErrorCode.CUSTOMER_EXISTS;

  protected CustomerRegisterError(final String code) {
    super(code);
  }

  public static CustomerRegisterError of(String code) {
    return new CustomerRegisterError(code);
  }
}
