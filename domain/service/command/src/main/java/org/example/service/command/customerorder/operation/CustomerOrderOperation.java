package org.example.service.command.customerorder.operation;

import static org.example.framework.result.Result.checkPreviousFutureResult;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.example.dto.graph.OrderData;
import org.example.framework.error.Error;
import org.example.framework.error.OperationError;
import org.example.framework.interaction.OperationInteraction;
import org.example.framework.result.Result;
import org.example.incoming.service.order.CustomerOrderServiceCommand;
import org.example.incoming.service.order.CustomerOrderServiceError;
import org.example.service.command.customerorder.domain.aggregate.CustomerOrder;
import org.example.service.command.customerorder.repository.CustomerOrderCommandRepositoryInteraction;

public class CustomerOrderOperation implements
    OperationInteraction<CustomerOrderServiceCommand, CustomerOrderOperationData,
        CustomerOrderServiceError, OrderData> {

  private final CustomerOrderCommandRepositoryInteraction customerCommandRepositoryInteraction;

  public CustomerOrderOperation(CustomerOrderCommandRepositoryInteraction customerCommandRepositoryInteraction) {
    this.customerCommandRepositoryInteraction = customerCommandRepositoryInteraction;
  }

  @Override
  public CompletableFuture<Result<OrderData, CustomerOrderServiceError>> execute(final CustomerOrderServiceCommand command) {
    return validate(command)
        .thenCompose(checkPreviousFutureResult(this::read))
        .thenCompose(checkPreviousFutureResult(this::operation))
        .thenCompose(checkPreviousFutureResult(this::write))
        .thenApply(this::combineResult);
  }


  @Override
  public CompletableFuture<Result<CustomerOrderOperationData, CustomerOrderServiceError>> validate(
      final CustomerOrderServiceCommand command) {
    if (command.order() == null) {
      return CompletableFuture.completedFuture(Result.error(CustomerOrderServiceError.of(
          OperationError.OTHER)));
    }
    CustomerOrderOperationData data = new CustomerOrderOperationData();
    data.setOrderIdentifier(command.order().identifier() != null
        ? Optional.ofNullable(command.order().identifier())
        : Optional.empty());
    data.setOrderData(command.order());
    return CompletableFuture.completedFuture(Result.ok(data));
  }

  @Override
  public CompletableFuture<Result<CustomerOrderOperationData, Error>> read(
      final CustomerOrderOperationData data) {
    return this.customerCommandRepositoryInteraction
        .read(data)
        .thenApply(result -> Result.ok(data));
  }

  @Override
  public CompletableFuture<Result<CustomerOrderOperationData, Error>> operation(final CustomerOrderOperationData data) {
    CustomerOrder aggregate = data.getCustomerOrder();
    return CompletableFuture.completedFuture(aggregate
        .createOrder(data.getOrderData())
        .map(result -> (CustomerOrderOperationData) data.setOrderChange(result))
        .mapError(e -> Error.of(e.code)));
  }

  @Override
  public CompletableFuture<Result<CustomerOrderOperationData, Error>> write(
      final CustomerOrderOperationData data) {
    return this.customerCommandRepositoryInteraction
        .write(data)
        .thenApply(result -> result.map(d -> data).mapError(e -> e));
  }

  @Override
  public Result<OrderData, CustomerOrderServiceError> combineResult(
      final Result<CustomerOrderOperationData, Error> result) {
    return result
        .map(CustomerOrderOperationData::getOrderData)
        .mapError(e -> CustomerOrderServiceError.of(e.code()));
  }
}
