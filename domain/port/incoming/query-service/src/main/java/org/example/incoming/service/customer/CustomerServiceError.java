package org.example.incoming.service.customer;

import java.util.Set;
import org.example.framework.error.Error;
import org.example.framework.error.OperationError;
import org.example.model.config.error.OperationErrorCode;

public class CustomerServiceError extends OperationError {

  public static String CUSTOMER_NOT_FOUND = OperationErrorCode.CUSTOMER_NOT_FOUND;

  private CustomerServiceError(String code) {
    super(code);
  }

  public static CustomerServiceError of(String code) {
    return new CustomerServiceError(code);
  }
}
