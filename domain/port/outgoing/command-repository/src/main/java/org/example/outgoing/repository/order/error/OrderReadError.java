package org.example.outgoing.repository.order.error;

import org.example.framework.error.OperationError;
import org.example.model.config.error.OperationErrorCode;

public class OrderReadError extends OperationError {

  public static String ORDER_NOT_FOUND = OperationErrorCode.ORDER_NOT_FOUND;

  protected OrderReadError(final String code) {
    super(code);
  }

  public static OrderReadError of(String code) {
    return new OrderReadError(code);
  }
}
