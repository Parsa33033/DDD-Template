package org.example.test.steps.stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import java.util.Map;
import java.util.UUID;
import org.example.dto.graph.CustomerData;
import org.example.dto.graph.ImmutableCustomerData;
import org.example.test.data.config.TestContext;
import org.example.test.data.model.CustomerTestData;
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

  public static CustomerData createCustomerData(DataTable dataTable) {
    Map<String, String> dataMap = dataTable.asMaps().get(0);
    UUID customerIdentifier = UUID.fromString(dataMap.get("customer_identifier"));
    String customerName = dataMap.get("customer_name");
    CustomerData customer = new CustomerTestBuilder(
        customerIdentifier,
        customerName).customerData();
    return customer;
  }

  private static class CustomerTestBuilder implements CustomerTestData {

    UUID identifier;
    String name;

    public CustomerTestBuilder(UUID identifier, String name) {
      this.identifier = identifier;
      this.name = name;
    }

    @Override
    public CustomerData customerData() {
      return ImmutableCustomerData.builder().identifier(identifier).name(name).build();
    }
  }
}
