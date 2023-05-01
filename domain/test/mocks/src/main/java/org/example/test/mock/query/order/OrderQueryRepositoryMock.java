package org.example.test.mock.query.order;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.example.dto.graph.OrderData;
import org.example.framework.result.Result;
import org.example.outgoing.repository.order.OrderQuery;
import org.example.outgoing.repository.order.OrderQueryError;
import org.example.outgoing.repository.order.OrderQueryRepository;
import org.example.test.mock.config.RepositoryMock;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

public class OrderQueryRepositoryMock implements RepositoryMock<OrderQueryRepository> {

  private final OrderQueryRepository orderQueryRepository;

  public OrderQueryRepositoryMock() {
    orderQueryRepository = Mockito.mock(OrderQueryRepository.class);
  }

  @Override
  public OrderQueryRepository repository() {
    return orderQueryRepository;
  }

  public void mockGetOrderByUUIDResult(
      final UUID identifier, final Result<OrderData, OrderQueryError> result) {
    Objects.requireNonNull(identifier, "identifier should not be null");
    Objects.requireNonNull(result, "result should not be null");
    doReturn(CompletableFuture.completedFuture(result))
        .when(orderQueryRepository)
        .getOrderByUUID(argThat(new FindByUUIDMatcher(identifier)));
  }

  public void mockGetOrderByUUIDResult(
      final ArgumentMatcher<OrderQuery> matcher,
      final Result<OrderData, OrderQueryError> result) {
    Objects.requireNonNull(matcher, "matcher should not be null");
    Objects.requireNonNull(result, "result should not be null");
    doReturn(CompletableFuture.completedFuture(result)).when(orderQueryRepository).getOrderByUUID(argThat(matcher));
  }

  public void mockAnyGetOrderByUUIDResult(final Result<OrderData, OrderQueryError> result) {
    Objects.requireNonNull(result, "result should not be null");
    doReturn(CompletableFuture.completedFuture(result)).when(orderQueryRepository).getOrderByUUID(any());
  }

  public void mockAnyGetOrderByUUIDThrows(final Throwable throwable) {
    doReturn(new CompletableFuture<>().completeExceptionally(throwable))
        .when(orderQueryRepository)
        .getOrderByUUID(any());
  }

  private static class FindByUUIDMatcher implements ArgumentMatcher<OrderQuery> {

    private final UUID identifier;

    FindByUUIDMatcher(UUID uuid) {
      this.identifier = uuid;
    }

    @Override
    public boolean matches(final OrderQuery orderQuery) {
      return orderQuery.orderIdentifier() != null && orderQuery
          .orderIdentifier()
          .equals(identifier);
    }
  }
}
