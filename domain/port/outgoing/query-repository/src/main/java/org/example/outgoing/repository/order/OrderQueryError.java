package org.example.outgoing.repository.order;

import org.example.framework.error.Error;
import org.example.model.config.error.OperationErrorCode;

public class OrderQueryError implements Error {

  public static String CUSTOMER_NOT_FOUND = OperationErrorCode.CUSTOMER_NOT_FOUND;


}
