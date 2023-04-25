package org.example.service.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import org.example.dto.graph.CustomerData;
import org.example.framework.result.Result;
import org.example.incoming.service.customer.CustomerCommandService;
import org.example.incoming.service.customer.CustomerServiceError;
import org.example.incoming.service.customer.ImmutableCustomerServiceCommand;
import org.example.service.command.customer.CustomerCommandServiceInteraction;
import org.example.test.data.config.TestContext;
import org.example.test.data.config.TestData;
import org.example.test.mock.command.customer.CustomerCommandRepositoryMockConfig;
import org.example.test.mock.config.ImmutableApplicationMockData;
import org.example.test.mock.config.MockData;
import org.example.test.persona.testdata.CustomerTest;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerCommandServiceInteractionStepDefs {

  TestContext testContext;

  private final CustomerCommandRepositoryMockConfig customerCommandRepositoryMockConfig;

  private final CustomerCommandService customerCommandService;

  private Result<CustomerData, CustomerServiceError> result;

  @Autowired
  public CustomerCommandServiceInteractionStepDefs(TestContext testContext) {
    this.testContext = testContext;
    customerCommandRepositoryMockConfig = new CustomerCommandRepositoryMockConfig();
    customerCommandService = new CustomerCommandServiceInteraction(
        customerCommandRepositoryMockConfig.repository());
  }

  @When("ensuring customer {customerTest}")
  public void customerIsQueried(CustomerTest customerTest)
      throws ExecutionException, InterruptedException {
    setupMockData();

    this.result = customerCommandService
        .ensureCustomer(ImmutableCustomerServiceCommand
            .builder()
            .customer(customerTest.customerData())
            .build())
        .get();

    this.testContext.result((Result) result);
  }

  @Then("customer {customerTest} is returned")
  public void customerIsReturned(CustomerTest customerTest) {
    assertTrue(result.isOk());
    assertEquals(result.object.identifier(), customerTest.customerData().identifier());
  }

  private void setupMockData() {
    MockData<TestData> mockData = ImmutableApplicationMockData
        .builder()
        .data(testContext.testData())
        .failureMap(new HashMap<>())
        .build();
    customerCommandRepositoryMockConfig.setupMockData(mockData);
  }
}
