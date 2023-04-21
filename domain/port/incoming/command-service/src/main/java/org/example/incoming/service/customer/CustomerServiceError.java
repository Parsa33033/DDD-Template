package org.example.incoming.service.customer;

import org.example.framework.error.Error;
import org.example.model.config.error.OperationErrorCode;

public class CustomerServiceError implements Error {

  public static String CUSTOMER_EXISTS = OperationErrorCode.CUSTOMER_EXISTS;
}
