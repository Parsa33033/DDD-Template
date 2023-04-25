package org.example.test.mock.query.customer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.example.dto.graph.CustomerData;
import org.example.framework.result.Result;
import org.example.outgoing.repository.customer.CustomerQuery;
import org.example.outgoing.repository.customer.CustomerQueryError;
import org.example.outgoing.repository.customer.CustomerQueryRepository;
import org.example.test.mock.config.RepositoryMock;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

public class CustomerQueryRepositoryMock implements RepositoryMock<CustomerQueryRepository> {

  private final CustomerQueryRepository customerQueryRepository;

  public CustomerQueryRepositoryMock() {
    customerQueryRepository = Mockito.mock(CustomerQueryRepository.class);
  }

  @Override
  public CustomerQueryRepository repository() {
    return customerQueryRepository;
  }

  public void mockGetCustomerByUUIDResult(
      final UUID identifier, final Result<CustomerData, CustomerQueryError> result) {
    Objects.requireNonNull(identifier, "identifier should not be null");
    Objects.requireNonNull(result, "result should not be null");
    doReturn(CompletableFuture.completedFuture(result))
        .when(customerQueryRepository)
        .getCustomerByUUID(argThat(new FindByUUIDMatcher(identifier)));
  }

  public void mockGetCustomerByUUIDResult(
      final ArgumentMatcher<CustomerQuery> matcher,
      final Result<CustomerData, CustomerQueryError> result) {
    Objects.requireNonNull(matcher, "matcher should not be null");
    Objects.requireNonNull(result, "result should not be null");
    doReturn(CompletableFuture.completedFuture(result)).when(customerQueryRepository).getCustomerByUUID(argThat(matcher));
  }

  public void mockAnyGetCustomerByUUIDResult(final Result<CustomerData, CustomerQueryError> result) {
    Objects.requireNonNull(result, "result should not be null");
    doReturn(CompletableFuture.completedFuture(result)).when(customerQueryRepository).getCustomerByUUID(any());
  }

  public void mockAnyGetCustomerByUUIDThrows(final Throwable throwable) {
    doReturn(new CompletableFuture<>().completeExceptionally(throwable))
        .when(customerQueryRepository)
        .getCustomerByUUID(any());
  }

  private static class FindByUUIDMatcher implements ArgumentMatcher<CustomerQuery> {

    private final UUID identifier;

    FindByUUIDMatcher(UUID uuid) {
      this.identifier = uuid;
    }

    @Override
    public boolean matches(final CustomerQuery customerQuery) {
      return customerQuery.customerIdentifier() != null && customerQuery
          .customerIdentifier()
          .equals(identifier);
    }
  }
}
