package org.example.service.query;

import io.cucumber.java.en.Given;
import org.example.test.data.config.TestData;
import org.example.test.data.config.TestDataInMemory;
import org.example.test.persona.testdata.CustomerTest;

public class CustomerQueryInteractionServiceStepDefs {

  TestData testData;

  public CustomerQueryInteractionServiceStepDefs() {
    testData = new TestDataInMemory();
  }

  @Given("customer {customerTest}")
  public void givenCustomer(CustomerTest customerTest) {
    testData.addCustomer(customerTest.customerData());
  }
}
