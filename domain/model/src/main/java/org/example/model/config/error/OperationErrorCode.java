package org.example.model.config.error;

import org.example.framework.error.ErrorCode;

public interface OperationErrorCode extends ErrorCode {

  String OTHER = "OTHER";
  String INVALID_REQUEST = "INVALID_REQUEST";

  // customer
  String CUSTOMER_NOT_FOUND = "CUSTOMER_NOT_FOUND";
  String CUSTOMER_EXISTS = "CUSTOMER_EXISTS";

  // order
  String ORDER_NOT_FOUND = "ORDER_NOT_FOUND";
  String ORDER_EXISTS = "ORDER_EXISTS";
}
