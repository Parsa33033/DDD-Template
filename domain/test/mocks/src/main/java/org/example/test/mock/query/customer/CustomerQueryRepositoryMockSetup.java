package org.example.test.mock.query.customer;

import java.util.Optional;
import org.example.dto.graph.CustomerData;
import org.example.framework.error.ImmutableError;
import org.example.framework.result.Result;
import org.example.outgoing.repository.customer.CustomerQuery;
import org.example.outgoing.repository.customer.CustomerQueryError;
import org.example.test.data.config.TestData;
import org.example.test.mock.config.MockData;
import org.example.test.mock.config.MockSetup;
import org.example.test.mock.config.failure.TestError;
import org.example.test.mock.config.failure.TestException;
import org.example.test.mock.config.failure.TestFailure;
import org.example.test.mock.config.RepositoryOperation;
import org.mockito.ArgumentMatcher;

public class CustomerQueryRepositoryMockSetup implements MockSetup<TestData> {

  CustomerQueryRepositoryMock repositoryMock;

  public CustomerQueryRepositoryMockSetup(CustomerQueryRepositoryMock repositoryMock) {
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
      Result<CustomerData, CustomerQueryError> result = Result.error(CustomerQueryError.of(
          CustomerQueryError.CUSTOMER_NOT_FOUND));
      repositoryMock.mockGetCustomerByUUIDResult(notFoundMatcher, result);
    } else {
      mockFailure(failure.get());
    }
  }

  @Override
  public void mockSuccess(MockData<TestData> mockData) {
    mockData
        .data()
        .getCustomers()
        .forEach(customer -> repositoryMock.mockGetCustomerByUUIDResult(customer.identifier(),
            Result.ok(customer)));
  }

  @Override
  public void mockFailure(TestFailure failure) {
    if (failure instanceof TestException) {
      repositoryMock.mockAnyGetCustomerByUUIDThrows(((TestException) failure).exception());
    } else if (failure instanceof TestFailure) {
      repositoryMock.mockAnyGetCustomerByUUIDResult(Result.error(CustomerQueryError.of(
          ImmutableError.of(((TestError) failure).error().code()).code())));
    }
  }

  private static class CustomerNotFoundMatcher implements ArgumentMatcher<CustomerQuery> {

    MockData<TestData> testData;

    public CustomerNotFoundMatcher(MockData<TestData> testData) {
      this.testData = testData;
    }

    @Override
    public boolean matches(final CustomerQuery customerQuery) {
      return testData
          .data()
          .getCustomers()
          .stream()
          .noneMatch(customerData -> customerData
              .identifier()
              .equals(customerQuery.customerIdentifier()));
    }
  }
}
