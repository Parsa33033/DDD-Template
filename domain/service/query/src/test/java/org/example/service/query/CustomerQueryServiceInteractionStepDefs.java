package org.example.service.query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import org.example.dto.graph.CustomerData;
import org.example.framework.result.Result;
import org.example.incoming.service.customer.CustomerQueryService;
import org.example.incoming.service.customer.CustomerServiceError;
import org.example.incoming.service.customer.ImmutableCustomerServiceQuery;
import org.example.service.query.customer.CustomerQueryServiceInteraction;
import org.example.test.data.config.TestContext;
import org.example.test.data.config.TestData;
import org.example.test.mock.config.ImmutableApplicationMockData;
import org.example.test.mock.config.MockData;
import org.example.test.mock.query.customer.CustomerQueryRepositoryMockConfig;
import org.example.test.persona.testdata.CustomerTest;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerQueryServiceInteractionStepDefs {

  TestContext testContext;

  private final CustomerQueryRepositoryMockConfig customerQueryRepositoryMockConfig;

  private final CustomerQueryService customerQueryService;

  private Result<CustomerData, CustomerServiceError> result;

  @Autowired
  public CustomerQueryServiceInteractionStepDefs(TestContext testContext) {
    this.testContext = testContext;
    customerQueryRepositoryMockConfig = new CustomerQueryRepositoryMockConfig();
    customerQueryService =
        new CustomerQueryServiceInteraction(customerQueryRepositoryMockConfig.repository());
  }

  @When("customer {customerTest} is queried")
  public void customerIsQueried(CustomerTest customerTest)
      throws ExecutionException, InterruptedException {
    setupMockData();

    this.result = customerQueryService.getCustomerByUUID(ImmutableCustomerServiceQuery
        .builder().customerIdentifier(customerTest.customerData()
        .identifier()).build()).get();

    this.testContext.result((Result) result);
  }

  @Then("customer {customerTest} is returned")
  public void customerIsReturned(CustomerTest customerTest) {
    assertTrue(result.isOk());
    assertTrue(result.object().identifier().equals(customerTest.customerData().identifier()));
  }

  @Then("customer result is returned with error {string}")
  public void customerIsError(String error) {
    assertTrue(result.isError());
    assertEquals(error, result.error().code);
  }

  private void setupMockData() {
    MockData<TestData> mockData = ImmutableApplicationMockData
        .builder()
        .data(testContext.testData())
        .failureMap(new HashMap<>())
        .build();
    customerQueryRepositoryMockConfig.setupMockData(mockData);
  }
}
