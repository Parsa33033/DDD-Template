package org.example.service.command.customerorder.repository;

import java.util.concurrent.CompletableFuture;
import org.example.dto.aggregate.ImmutableCustomerOrderData;
import org.example.dto.graph.OrderData;
import org.example.framework.error.Error;
import org.example.framework.interaction.RepositoryInteraction;
import org.example.framework.result.Result;
import org.example.outgoing.repository.order.OrderCommandRepository;
import org.example.outgoing.repository.order.dto.ImmutableOrderReadCommand;
import org.example.outgoing.repository.order.dto.ImmutableOrderWriteCommand;
import org.example.outgoing.repository.order.dto.OrderWriteCommand;
import org.example.service.command.customerorder.CustomerOrderCommandServiceInteractionData;
import org.example.service.command.customerorder.domain.aggregate.CustomerOrder;

public class CustomerOrderCommandRepositoryInteraction implements
    RepositoryInteraction<CustomerOrderCommandServiceInteractionData, OrderData, CustomerOrder> {

  private final OrderCommandRepository repository;

  public CustomerOrderCommandRepositoryInteraction(OrderCommandRepository repository) {
    this.repository = repository;
  }

  @Override
  public <T extends CustomerOrderCommandServiceInteractionData> CompletableFuture<Result<T, Error>> read(
      final T data) {
    return repository
        .read(ImmutableOrderReadCommand
            .builder()
            .orderIdentifier(data.getOrderIdentifier().orElse(null))
            .build())
        .thenApply(result -> result
            .map(d -> updateAggregateRoot(data, CustomerOrder.fromDataTransferObject(d)))
            .mapError(e -> {
              newAggregateRoot(data);
              return Error.of(e.code);
            }));
  }

  @Override
  public <T extends CustomerOrderCommandServiceInteractionData> CompletableFuture<Result<OrderData,
      Error>> write(
      final T data) {
    OrderWriteCommand command = ImmutableOrderWriteCommand
        .builder()
        .order(data.getOrderChange().orderData())
        .orderChange(data.getOrderChange())
        .build();
    if (noChange(data)) {
      return CompletableFuture.completedFuture(Result.ok(data.getOrderChange().orderData()));
    }
    return repository
        .write(command)
        .thenApply(result -> result
            .map(d -> createChangeIsApplied(data) ? data
                .getOrderChange()
                .createOrder()
                .orderData() : data.getOrderChange().updateOrder().orderData())
            .mapError(e -> Error.of(e.code)));
  }

  @Override
  public <T extends CustomerOrderCommandServiceInteractionData> T updateAggregateRoot(
      T data, CustomerOrder aggregateRoot) {
    data.setCustomerOrder(aggregateRoot);
    return data;
  }

  public <T extends CustomerOrderCommandServiceInteractionData> T newAggregateRoot(
      T data) {
    CustomerOrder aggregateRoot = CustomerOrder.fromDataTransferObject(
        ImmutableCustomerOrderData.builder().build());
    data.setCustomerOrder(aggregateRoot);
    return data;
  }

  @Override
  public <T extends CustomerOrderCommandServiceInteractionData> boolean noChange(final T data) {
    return data.getOrderChange() == null || ( data.getOrderChange().createOrder() == null
        && data.getOrderChange().updateOrder() == null);
  }

  public boolean createChangeIsApplied(final CustomerOrderCommandServiceInteractionData data) {
    return data.getOrderChange() != null && data.getOrderChange().createOrder() != null;
  }
}
