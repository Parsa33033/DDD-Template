package org.example.service.query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import org.example.dto.graph.OrderData;
import org.example.framework.result.Result;
import org.example.incoming.service.order.OrderQueryService;
import org.example.incoming.service.order.OrderServiceError;
import org.example.incoming.service.order.ImmutableOrderServiceQuery;
import org.example.service.query.order.OrderQueryServiceInteraction;
import org.example.test.data.config.TestContext;
import org.example.test.data.config.TestData;
import org.example.test.mock.config.ImmutableApplicationMockData;
import org.example.test.mock.config.MockData;
import org.example.test.mock.query.order.OrderQueryRepositoryMockConfig;
import org.example.test.persona.testdata.OrderTest;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderQueryServiceInteractionStepDefs {

  TestContext testContext;

  private final OrderQueryRepositoryMockConfig orderQueryRepositoryMockConfig;

  private final OrderQueryService orderQueryService;

  private Result<OrderData, OrderServiceError> result;

  @Autowired
  public OrderQueryServiceInteractionStepDefs(TestContext testContext) {
    this.testContext = testContext;
    orderQueryRepositoryMockConfig = new OrderQueryRepositoryMockConfig();
    orderQueryService =
        new OrderQueryServiceInteraction(orderQueryRepositoryMockConfig.repository());
  }

  @When("order {orderTest} is queried")
  public void orderIsQueried(OrderTest orderTest)
      throws ExecutionException, InterruptedException {
    setupMockData();

    this.result = orderQueryService.getOrderByUUID(ImmutableOrderServiceQuery
        .builder().orderIdentifier(orderTest.orderData()
        .identifier()).build()).get();

    this.testContext.result((Result) result);
  }

  @Then("order {orderTest} is returned")
  public void orderIsReturned(OrderTest orderTest) {
    assertTrue(result.isOk());
    assertTrue(result.object().identifier().equals(orderTest.orderData().identifier()));
  }

  @Then("order result is returned with error {string}")
  public void orderIsError(String error) {
    assertTrue(result.isError());
    assertEquals(error, result.error().code);
  }

  private void setupMockData() {
    MockData<TestData> mockData = ImmutableApplicationMockData
        .builder()
        .data(testContext.testData())
        .failureMap(new HashMap<>())
        .build();
    orderQueryRepositoryMockConfig.setupMockData(mockData);
  }
}
