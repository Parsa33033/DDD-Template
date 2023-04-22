package org.example.test.steps.types;

import io.cucumber.java.ParameterType;
import org.example.test.persona.testdata.CustomerTest;

public class GeneralParameterType {

  @ParameterType("\\w*")
  public CustomerTest customerTest(String name) {
    return CustomerTest.valueOf(name);
  }
}
