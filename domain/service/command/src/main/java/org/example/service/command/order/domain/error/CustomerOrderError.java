package org.example.service.command.order.domain.error;

import org.example.framework.error.OperationError;
import org.example.model.config.error.OperationErrorCode;

public class CustomerOrderError extends OperationError {

  public static String ORDER_ALREADY_EXISTS = OperationErrorCode.ORDER_EXISTS;

  protected CustomerOrderError(final String code) {
    super(code);
  }

  public static CustomerOrderError of(String code) {
    return new CustomerOrderError(code);
  }
}
