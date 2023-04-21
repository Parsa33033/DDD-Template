package org.example.outgoing.repository.customer;

import java.util.Set;
import org.example.framework.error.Error;
import org.example.framework.error.OperationError;
import org.example.model.config.error.OperationErrorCode;

public class CustomerQueryError extends OperationError {

  public static String CUSTOMER_NOT_FOUND = OperationErrorCode.CUSTOMER_NOT_FOUND;

  private Set<String> errors() {
    return Set.of(CUSTOMER_NOT_FOUND);
  }

  private CustomerQueryError(String code) {
    super(code);
  }

  public static CustomerQueryError of(String code) {
    return new CustomerQueryError(code);
  }
}
