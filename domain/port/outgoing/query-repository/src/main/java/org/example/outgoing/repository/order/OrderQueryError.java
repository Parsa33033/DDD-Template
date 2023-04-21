package org.example.outgoing.repository.order;

import org.example.framework.error.Error;
import org.example.framework.error.OperationError;
import org.example.model.config.error.OperationErrorCode;

public class OrderQueryError extends OperationError {

  public static String ORDER_NOT_FOUND = OperationErrorCode.ORDER_NOT_FOUND;

  private OrderQueryError(String code) {
    super(code);
  }

  public static OrderQueryError of(String code) {
    return new OrderQueryError(code);
  }
}
