package org.example.test.mock.query.order;

import java.util.Optional;
import org.example.dto.graph.OrderData;
import org.example.framework.error.ImmutableError;
import org.example.framework.result.Result;
import org.example.outgoing.repository.order.OrderQuery;
import org.example.outgoing.repository.order.OrderQueryError;
import org.example.test.data.config.TestData;
import org.example.test.mock.config.MockData;
import org.example.test.mock.config.MockSetup;
import org.example.test.mock.config.RepositoryOperation;
import org.example.test.mock.config.failure.TestError;
import org.example.test.mock.config.failure.TestException;
import org.example.test.mock.config.failure.TestFailure;
import org.mockito.ArgumentMatcher;

public class OrderQueryRepositoryMockSetup implements MockSetup<TestData> {

  OrderQueryRepositoryMock repositoryMock;

  public OrderQueryRepositoryMockSetup(OrderQueryRepositoryMock repositoryMock) {
    this.repositoryMock = repositoryMock;
  }

  @Override
  public void setupMockData(final MockData<TestData> mockData) {
    Optional<TestFailure> failure = Optional.ofNullable(mockData
        .failureMap()
        .get(RepositoryOperation.ORDER_GET_BY_UUID));
    if (failure.isEmpty()) {
      mockSuccess(mockData);
      OrderNotFoundMatcher notFoundMatcher = new OrderNotFoundMatcher(mockData);
      Result<OrderData, OrderQueryError> result = Result.error(OrderQueryError.of(
          OrderQueryError.ORDER_NOT_FOUND));
      repositoryMock.mockGetOrderByUUIDResult(notFoundMatcher, result);
    } else {
      mockFailure(failure.get());
    }
  }

  @Override
  public void mockSuccess(MockData<TestData> mockData) {
    mockData
        .data()
        .getOrders()
        .forEach(order -> repositoryMock.mockGetOrderByUUIDResult(order.identifier(),
            Result.ok(order)));
  }

  @Override
  public void mockFailure(TestFailure failure) {
    if (failure instanceof TestException) {
      repositoryMock.mockAnyGetOrderByUUIDThrows(((TestException) failure).exception());
    } else if (failure instanceof TestFailure) {
      repositoryMock.mockAnyGetOrderByUUIDResult(Result.error(OrderQueryError.of(
          ImmutableError.of(((TestError) failure).error().code()).code())));
    }
  }

  private static class OrderNotFoundMatcher implements ArgumentMatcher<OrderQuery> {

    MockData<TestData> testData;

    public OrderNotFoundMatcher(MockData<TestData> testData) {
      this.testData = testData;
    }

    @Override
    public boolean matches(final OrderQuery orderQuery) {
      return testData
          .data()
          .getOrders()
          .stream()
          .noneMatch(orderData -> orderData
              .identifier()
              .equals(orderQuery.orderIdentifier()));
    }
  }
}
