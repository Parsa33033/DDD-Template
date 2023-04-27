package org.example.service.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import org.example.dto.base.ImmutableCustomerReferenceData;
import org.example.dto.graph.ImmutableOrderData;
import org.example.dto.graph.OrderData;
import org.example.framework.result.Result;
import org.example.incoming.service.order.CustomerOrderCommandService;
import org.example.incoming.service.order.CustomerOrderServiceError;
import org.example.incoming.service.order.ImmutableCustomerOrderServiceCommand;
import org.example.service.command.customerorder.CustomerOrderCommandServiceInteraction;
import org.example.test.data.config.TestContext;
import org.example.test.data.config.TestData;
import org.example.test.mock.command.customer.CustomerCommandRepositoryMockConfig;
import org.example.test.mock.command.customerorder.OrderCommandRepositoryMockConfig;
import org.example.test.mock.config.ImmutableApplicationMockData;
import org.example.test.mock.config.MockData;
import org.example.test.persona.testdata.CustomerTest;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerOrderCommandServiceInteractionStepDefs {

  TestContext testContext;

  private final OrderCommandRepositoryMockConfig orderCommandRepositoryMockConfig;

  private final CustomerOrderCommandService customerOrderCommandService;

  private Result<OrderData, CustomerOrderServiceError> result;

  @Autowired
  public CustomerOrderCommandServiceInteractionStepDefs(TestContext testContext) {
    this.testContext = testContext;
    orderCommandRepositoryMockConfig = new OrderCommandRepositoryMockConfig();
    customerOrderCommandService = new CustomerOrderCommandServiceInteraction(
        orderCommandRepositoryMockConfig.repository());
  }

  @When("customer {customerTest} orders {string}")
  public void customerIsQueried(CustomerTest customerTest, String order)
      throws ExecutionException, InterruptedException {
    setupMockData();

    this.result = customerOrderCommandService
        .createOrder(ImmutableCustomerOrderServiceCommand
            .builder()
            .order(ImmutableOrderData
                .builder()
                .customerReferenceData(ImmutableCustomerReferenceData
                    .builder()
                    .customerIdentifier(customerTest.customerData().identifier())
                    .build())
                .productName(order)
                .build())
            .build())
        .get();

    this.testContext.result((Result) result);
  }

  @Then("result of order is ok")
  public void resultIsOk() {
    assertNotNull(result, "result should not be null");
    assertTrue(result.isOk(), "result should be ok");
  }

  @Then("order {string} was returned for customer {customerTest}")
  public void orderWasReturnedForCustomer(String order, CustomerTest customerTest) {
    assertEquals(order, result.object().productName());
    assertEquals(
        customerTest.customerData().identifier(),
        result.object().customerReferenceData().customerIdentifier());
  }

  @Then("order {string} was created")
  public void orderWasCreatedForCustomer(String order) {
    assertTrue(orderCommandRepositoryMockConfig
        .captureAllCommands()
        .stream()
        .anyMatch(wc -> order.equals(wc.orderChange().createOrder().orderData().productName())));
  }

  private void setupMockData() {
    MockData<TestData> mockData = ImmutableApplicationMockData
        .builder()
        .data(testContext.testData())
        .failureMap(new HashMap<>())
        .build();
    orderCommandRepositoryMockConfig.setupMockData(mockData);
  }
}
