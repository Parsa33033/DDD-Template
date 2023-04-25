package org.example.service.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import org.example.dto.graph.CustomerData;
import org.example.framework.result.Result;
import org.example.incoming.service.customer.CustomerCommandService;
import org.example.incoming.service.customer.CustomerServiceError;
import org.example.incoming.service.customer.ImmutableCustomerServiceCommand;
import org.example.outgoing.repository.customer.dto.CustomerWriteCommand;
import org.example.service.command.customer.CustomerCommandServiceInteraction;
import org.example.test.data.config.TestContext;
import org.example.test.data.config.TestData;
import org.example.test.mock.command.customer.CustomerCommandRepositoryMockConfig;
import org.example.test.mock.config.ImmutableApplicationMockData;
import org.example.test.mock.config.MockData;
import org.example.test.persona.testdata.CustomerTest;
import org.example.test.steps.stepdefs.CustomerStepDefs;
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

  @When("ensuring customer")
  public void customerIsQueriedWith(DataTable dataTable)
      throws ExecutionException, InterruptedException {
    setupMockData();

    CustomerData customer = CustomerStepDefs.createCustomerData(dataTable);

    this.result = customerCommandService
        .ensureCustomer(ImmutableCustomerServiceCommand.builder().customer(customer).build())
        .get();

    this.testContext.result((Result) result);
  }

  @Then("customer {customerTest} is returned")
  public void customerIsReturned(CustomerTest customerTest) {
    assertEquals(result.object().identifier(), customerTest.customerData().identifier());
  }

  @Then("customer {customerTest} is created")
  public void customerIsCreated(CustomerTest customerTest) {
    CustomerWriteCommand command = customerCommandRepositoryMockConfig.captureCommand(customerTest
        .customerData()
        .identifier());
    assertNotNull(command, "command should not be null");
    assertEquals(
        customerTest.customerData(),
        command.customerChange().createCustomer().customerData());
  }

  @Then("returned customer is")
  public void returnedCustomerIs(DataTable dataTable) {
    assertEquals(result.object(), CustomerStepDefs.createCustomerData(dataTable));
  }


  @Then("result is ok")
  public void resultIsOk() {
    assertNotNull(result, "result should not be null");
    assertTrue(result.isOk(), "result should be ok");
  }

  @Then("customer is")
  public void customerIsReturned(DataTable dataTable) {
    assertTrue(result.isOk());

    assertEquals(result.object(), CustomerStepDefs.createCustomerData(dataTable));
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
