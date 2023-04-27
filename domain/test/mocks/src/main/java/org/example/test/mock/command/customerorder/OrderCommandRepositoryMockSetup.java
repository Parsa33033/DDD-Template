package org.example.test.mock.command.customerorder;

import java.util.Optional;
import java.util.Set;
import org.example.dto.aggregate.CustomerOrderData;
import org.example.dto.aggregate.CustomerRegisterData;
import org.example.dto.aggregate.ImmutableCustomerOrderData;
import org.example.dto.aggregate.ImmutableCustomerRegisterData;
import org.example.framework.error.ImmutableError;
import org.example.framework.result.Nothing;
import org.example.framework.result.Result;
import org.example.outgoing.repository.customer.dto.CustomerReadCommand;
import org.example.outgoing.repository.customer.error.CustomerReadError;
import org.example.outgoing.repository.order.dto.OrderReadCommand;
import org.example.outgoing.repository.order.error.OrderReadError;
import org.example.test.data.config.TestData;
import org.example.test.mock.config.MockData;
import org.example.test.mock.config.MockSetup;
import org.example.test.mock.config.RepositoryOperation;
import org.example.test.mock.config.failure.TestError;
import org.example.test.mock.config.failure.TestException;
import org.example.test.mock.config.failure.TestFailure;
import org.mockito.ArgumentMatcher;

public class OrderCommandRepositoryMockSetup implements MockSetup<TestData> {

  OrderCommandRepositoryMock repositoryMock;

  public OrderCommandRepositoryMockSetup(OrderCommandRepositoryMock repositoryMock) {
    this.repositoryMock = repositoryMock;
  }

  @Override
  public void setupMockData(final MockData<TestData> mockData) {
    Optional<TestFailure> failure = Optional.ofNullable(mockData
        .failureMap()
        .get(RepositoryOperation.CUSTOMER_GET_BY_UUID));
    if (failure.isEmpty()) {
      mockSuccess(mockData);
      OrderNotFoundMatcher notFoundMatcher = new OrderNotFoundMatcher(mockData);
      Result<CustomerOrderData, OrderReadError> result = Result.error(OrderReadError.of(
          CustomerReadError.CUSTOMER_NOT_FOUND));
      repositoryMock.mockGetResult(notFoundMatcher, result);
    } else {
      mockFailure(failure.get());
    }
  }

  @Override
  public void mockSuccess(MockData<TestData> mockData) {
    mockData.data().getOrders().forEach(order -> repositoryMock.mockGetResult(
        order.identifier(),
        Result.ok(ImmutableCustomerOrderData
            .builder()
            .customer(mockData
                .data()
                .getCustomerById(order.customerReferenceData().customerIdentifier())
                .get())
            .customerOrders(Set.of(order))
            .build())));
    repositoryMock.mockAnyWriteResult(Result.ok(Nothing.get()));
  }

  @Override
  public void mockFailure(TestFailure failure) {
    if (failure instanceof TestException) {
      repositoryMock.mockAnyGetThrows(((TestException) failure).exception());
    } else if (failure instanceof TestFailure) {
      repositoryMock.mockAnyGetResult(Result.error(OrderReadError.of(ImmutableError
          .of(((TestError) failure).error().code())
          .code())));
    }
  }

  private static class OrderNotFoundMatcher implements ArgumentMatcher<OrderReadCommand> {

    MockData<TestData> testData;

    public OrderNotFoundMatcher(MockData<TestData> testData) {
      this.testData = testData;
    }

    @Override
    public boolean matches(final OrderReadCommand orderReadCommand) {
      return testData
          .data()
          .getOrders()
          .stream()
          .noneMatch(orderData -> orderData
              .identifier()
              .equals(orderReadCommand.orderIdentifier()));
    }
  }
}
