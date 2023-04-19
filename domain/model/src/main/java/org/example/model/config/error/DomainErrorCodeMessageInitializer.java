package org.example.model.config.error;

import static org.example.model.config.error.FieldErrorCode.NULL_POINTER;

import org.example.framework.error.ErrorCodeMessageInitializer;

public class DomainErrorCodeMessageInitializer extends ErrorCodeMessageInitializer {


  @Override
  public void initialize() {
    addFieldErrors();
  }

  private void addFieldErrors() {
    addMessage(NULL_POINTER, "null pointer was found");
  }
}
