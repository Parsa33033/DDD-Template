package org.example.test.mock.command.customerorder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.example.dto.aggregate.CustomerOrderData;
import org.example.framework.result.Nothing;
import org.example.framework.result.Result;
import org.example.outgoing.repository.customer.dto.CustomerWriteCommand;
import org.example.outgoing.repository.order.OrderCommandRepository;
import org.example.outgoing.repository.order.dto.OrderReadCommand;
import org.example.outgoing.repository.order.dto.OrderWriteCommand;
import org.example.outgoing.repository.order.error.OrderReadError;
import org.example.outgoing.repository.order.error.OrderWriteError;
import org.example.test.mock.config.AggregateCommandRepositoryMock;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

public class OrderCommandRepositoryMock implements
    AggregateCommandRepositoryMock<OrderCommandRepository, OrderReadCommand, CustomerOrderData, OrderReadError, OrderWriteCommand, Nothing, OrderWriteError> {

  private final OrderCommandRepository orderCommandRepository;

  private final ArgumentCaptor<OrderWriteCommand> orderWriteCommandArgumentCaptor;

  public OrderCommandRepositoryMock() {
    orderCommandRepository = Mockito.mock(OrderCommandRepository.class);
    orderWriteCommandArgumentCaptor = ArgumentCaptor.forClass(OrderWriteCommand.class);
  }

  @Override
  public OrderCommandRepository repository() {
    return orderCommandRepository;
  }

  @Override
  public OrderWriteCommand captureWriteCommand(final UUID identifier) {
    Objects.requireNonNull(identifier, "identifier should not be null");
    return orderWriteCommandArgumentCaptor
        .getAllValues()
        .stream()
        .filter(wc -> wc.order() == null || identifier.equals(wc.order().identifier()))
        .filter(wc -> wc.orderChange().createOrder() == null || identifier.equals(wc
            .orderChange()
            .createOrder().orderData().identifier()))
        .filter(wc -> wc.orderChange().updateOrder() == null || identifier.equals(wc
            .orderChange()
            .updateOrder().orderData().identifier()))
        .findAny()
        .orElse(null);
  }

  @Override
  public List<OrderWriteCommand> captureAllWriteCommands() {
    return orderWriteCommandArgumentCaptor.getAllValues();
  }

  @Override
  public void mockGetResult(
      final UUID identifier, final Result<CustomerOrderData, OrderReadError> result) {
    Objects.requireNonNull(identifier, "identifier should not be null");
    Objects.requireNonNull(result, "result should not be null");
    doReturn(CompletableFuture.completedFuture(result))
        .when(orderCommandRepository)
        .read(argThat(new FindByUUIDMatcher(identifier)));
  }

  @Override
  public void mockGetResult(
      final ArgumentMatcher<OrderReadCommand> matcher,
      final Result<CustomerOrderData, OrderReadError> result) {
    Objects.requireNonNull(matcher, "matcher should not be null");
    Objects.requireNonNull(result, "result should not be null");
    doReturn(CompletableFuture.completedFuture(result))
        .when(orderCommandRepository)
        .read(argThat(matcher));
  }

  @Override
  public void mockAnyGetResult(final Result<CustomerOrderData, OrderReadError> result) {
    Objects.requireNonNull(result, "result should not be null");
    doReturn(CompletableFuture.completedFuture(result)).when(orderCommandRepository).read(any());
  }

  @Override
  public void mockAnyGetThrows(final Throwable throwable) {
    doReturn(new CompletableFuture<>().completeExceptionally(throwable))
        .when(orderCommandRepository)
        .read(any());
  }

  @Override
  public void mockAnyWriteResult(
      final ArgumentMatcher<OrderWriteCommand> matcher,
      final Result<Nothing, OrderWriteError> result) {
    Objects.requireNonNull(result, "result should not be null for write");
    doReturn(CompletableFuture.completedFuture(result))
        .when(orderCommandRepository)
        .write(any());
  }

  @Override
  public void mockAnyWriteResult(final Result<Nothing, OrderWriteError> result) {
    Objects.requireNonNull(result, "result should not be null for write");
    doReturn(CompletableFuture.completedFuture(result))
        .when(orderCommandRepository)
        .write(orderWriteCommandArgumentCaptor.capture());
  }

  private static class FindByUUIDMatcher implements ArgumentMatcher<OrderReadCommand> {

    private final UUID identifier;

    FindByUUIDMatcher(UUID uuid) {
      this.identifier = uuid;
    }

    @Override
    public boolean matches(final OrderReadCommand orderReadCommand) {
      return orderReadCommand.orderIdentifier() != null && orderReadCommand
          .orderIdentifier()
          .equals(identifier);
    }
  }
}
