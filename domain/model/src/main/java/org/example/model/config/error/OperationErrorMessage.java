package org.example.model.config.error;

import org.example.framework.error.ErrorMessage;

public interface OperationErrorMessage extends ErrorMessage {
  public static String MSG_OTHER = "error is other";
  public static String MSG_CUSTOMER_NOT_FOUND = "customer is not found";
}