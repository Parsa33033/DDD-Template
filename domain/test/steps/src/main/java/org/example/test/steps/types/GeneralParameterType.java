package org.example.test.steps.types;

import io.cucumber.java.ParameterType;
import org.example.test.persona.testdata.CustomerTest;
import org.example.test.persona.testdata.OrderTest;

public class GeneralParameterType {

  @ParameterType("\\w*")
  public CustomerTest customerTest(String name) {
    return CustomerTest.valueOf(name);
  }

  @ParameterType("\\w*")
  public OrderTest orderTest(String name) {
    return OrderTest.valueOf(name);
  }
}
