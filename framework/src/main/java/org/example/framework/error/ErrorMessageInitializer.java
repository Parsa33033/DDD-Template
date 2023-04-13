package org.example.framework.error;

public interface ErrorMessageInitializer {

  void initialize();

  void addMessage(String code, String message);
}
