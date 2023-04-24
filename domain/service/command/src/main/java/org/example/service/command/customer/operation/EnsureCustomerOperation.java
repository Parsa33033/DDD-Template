package org.example.service.command.customer.operation;

import static org.example.framework.result.Result.checkPreviousFutureResult;

import java.util.concurrent.CompletableFuture;
import org.example.dto.graph.CustomerData;
import org.example.framework.error.Error;
import org.example.framework.interaction.OperationInteraction;
import org.example.framework.result.Result;
import org.example.incoming.service.customer.CustomerServiceCommand;
import org.example.incoming.service.customer.CustomerServiceError;
import org.example.service.command.customer.domain.aggregate.CustomerRegister;
import org.example.service.command.customer.repository.CustomerCommandRepositoryInteraction;

public class EnsureCustomerOperation implements
    OperationInteraction<CustomerServiceCommand, EnsureCustomerOperationData,
        CustomerServiceError, CustomerData> {

  private final CustomerCommandRepositoryInteraction customerCommandRepositoryInteraction;

  public EnsureCustomerOperation(CustomerCommandRepositoryInteraction customerCommandRepositoryInteraction) {
    this.customerCommandRepositoryInteraction = customerCommandRepositoryInteraction;
  }

  @Override
  public CompletableFuture<Result<CustomerData, CustomerServiceError>> execute(final CustomerServiceCommand command) {
    return validate(command)
        .thenCompose(checkPreviousFutureResult(this::read))
        .thenCompose(checkPreviousFutureResult(this::operation))
        .thenCompose(checkPreviousFutureResult(this::write))
        .thenApply(this::combineResult);
  }


  @Override
  public CompletableFuture<Result<EnsureCustomerOperationData, CustomerServiceError>> validate(
      final CustomerServiceCommand command) {
    return null;
  }

  @Override
  public CompletableFuture<Result<EnsureCustomerOperationData, Error>> read(
      final EnsureCustomerOperationData data) {
    return this.customerCommandRepositoryInteraction.read(data);
  }

  @Override
  public CompletableFuture<Result<EnsureCustomerOperationData, Error>> operation(final EnsureCustomerOperationData data) {
    CustomerRegister aggregate = data.getCustomerRegister();
    return CompletableFuture.completedFuture(aggregate
        .ensureCustomerIsRegistered(data.getCustomerData())
        .map(result -> (EnsureCustomerOperationData) data.setCustomerChange(result))
        .mapError(e -> Error.of(e.code)));
  }

  @Override
  public CompletableFuture<Result<EnsureCustomerOperationData, Error>> write(
      final EnsureCustomerOperationData data) {
    return this.customerCommandRepositoryInteraction
        .write(data)
        .thenApply(result -> result.map(d -> data).mapError(e -> e));
  }

  @Override
  public Result<CustomerData, CustomerServiceError> combineResult(
      final Result<EnsureCustomerOperationData, Error> result) {
    return result
        .map(EnsureCustomerOperationData::getCustomerData)
        .mapError(e -> CustomerServiceError.of(e.code()));
  }
}
