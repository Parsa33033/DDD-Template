package org.example.test.steps.stepdefs;

import io.cucumber.java.en.Given;
import org.example.test.data.config.TestContext;
import org.example.test.persona.testdata.CustomerTest;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerStepDefs {

  TestContext testContext;

  @Autowired
  public CustomerStepDefs(TestContext testContext) {
    this.testContext = testContext;
  }

  @Given("customer {customerTest}")
  public void givenCustomer(CustomerTest customerTest) {
    testContext.testData().addCustomer(customerTest.customerData());
  }
}
