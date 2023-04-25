package org.example.test.mock.command.customer;

import java.util.Optional;
import org.example.dto.aggregate.CustomerRegisterData;
import org.example.dto.aggregate.ImmutableCustomerRegisterData;
import org.example.dto.graph.CustomerData;
import org.example.framework.error.ImmutableError;
import org.example.framework.result.Nothing;
import org.example.framework.result.Result;
import org.example.outgoing.repository.customer.CustomerQuery;
import org.example.outgoing.repository.customer.CustomerQueryError;
import org.example.outgoing.repository.customer.dto.CustomerReadCommand;
import org.example.outgoing.repository.customer.error.CustomerReadError;
import org.example.test.data.config.TestData;
import org.example.test.mock.config.MockData;
import org.example.test.mock.config.MockSetup;
import org.example.test.mock.config.failure.TestError;
import org.example.test.mock.config.failure.TestException;
import org.example.test.mock.config.failure.TestFailure;
import org.example.test.mock.config.RepositoryOperation;
import org.example.test.mock.query.customer.CustomerQueryRepositoryMock;
import org.mockito.ArgumentMatcher;

public class CustomerCommandRepositoryMockSetup implements MockSetup<TestData> {

  CustomerCommandRepositoryMock repositoryMock;

  public CustomerCommandRepositoryMockSetup(CustomerCommandRepositoryMock repositoryMock) {
    this.repositoryMock = repositoryMock;
  }

  @Override
  public void setupMockData(final MockData<TestData> mockData) {
    Optional<TestFailure> failure = Optional.ofNullable(mockData
        .failureMap()
        .get(RepositoryOperation.CUSTOMER_GET_BY_UUID));
    if (failure.isEmpty()) {
      mockSuccess(mockData);
      CustomerNotFoundMatcher notFoundMatcher = new CustomerNotFoundMatcher(mockData);
      Result<CustomerRegisterData, CustomerReadError> result = Result.error(CustomerReadError.of(
          CustomerReadError.CUSTOMER_NOT_FOUND));
      repositoryMock.mockGetResult(notFoundMatcher, result);
    } else {
      mockFailure(failure.get());
    }
  }

  @Override
  public void mockSuccess(MockData<TestData> mockData) {
    mockData.data().getCustomers().forEach(customer -> repositoryMock.mockGetResult(
        customer.identifier(),
        Result.ok(ImmutableCustomerRegisterData.builder().customerData(customer).build())));
    repositoryMock.mockAnyWriteResult(Result.ok(Nothing.get()));
  }

  @Override
  public void mockFailure(TestFailure failure) {
    if (failure instanceof TestException) {
      repositoryMock.mockAnyGetThrows(((TestException) failure).exception());
    } else if (failure instanceof TestFailure) {
      repositoryMock.mockAnyGetResult(Result.error(CustomerReadError.of(ImmutableError
          .of(((TestError) failure).error().code())
          .code())));
    }
  }

  private static class CustomerNotFoundMatcher implements ArgumentMatcher<CustomerReadCommand> {

    MockData<TestData> testData;

    public CustomerNotFoundMatcher(MockData<TestData> testData) {
      this.testData = testData;
    }

    @Override
    public boolean matches(final CustomerReadCommand customerReadCommand) {
      return testData
          .data()
          .getCustomers()
          .stream()
          .noneMatch(customerData -> customerData
              .identifier()
              .equals(customerReadCommand.customerIdentifier()));
    }
  }
}
