package org.example.outgoing.repository.customer.error;

import org.example.framework.error.Error;
import org.example.model.config.error.OperationErrorCode;

public class CustomerWriteError implements Error {

  private static String CUSTOMER_EXISTS = OperationErrorCode.CUSTOMER_EXISTS;
}
