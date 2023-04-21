package org.example.outgoing.repository.customer.error;

import org.example.framework.error.Error;
import org.example.model.config.error.OperationErrorCode;

public class CustomerReadError implements Error {

  private static String CUSTOMER_NOT_FOUND = OperationErrorCode.CUSTOMER_NOT_FOUND;
}
