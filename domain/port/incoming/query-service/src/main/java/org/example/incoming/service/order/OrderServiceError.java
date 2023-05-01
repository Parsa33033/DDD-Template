package org.example.incoming.service.order;

import org.example.framework.error.OperationError;
import org.example.model.config.error.OperationErrorCode;

public class OrderServiceError extends OperationError {

  public static String ORDER_NOT_FOUND = OperationErrorCode.ORDER_NOT_FOUND;

  private OrderServiceError(String code) {
    super(code);
  }

  public static OrderServiceError of(String code) {
    return new OrderServiceError(code);
  }
}
