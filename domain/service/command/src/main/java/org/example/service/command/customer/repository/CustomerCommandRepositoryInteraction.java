package org.example.service.command.customer.repository;

import java.util.concurrent.CompletableFuture;
import org.example.dto.graph.CustomerData;
import org.example.framework.error.Error;
import org.example.framework.interaction.RepositoryInteraction;
import org.example.framework.result.Nothing;
import org.example.framework.result.Result;
import org.example.outgoing.dto.change.CustomerChange;
import org.example.outgoing.repository.customer.CustomerCommandRepository;
import org.example.outgoing.repository.customer.dto.CustomerReadCommand;
import org.example.outgoing.repository.customer.dto.CustomerWriteCommand;
import org.example.outgoing.repository.customer.dto.ImmutableCustomerReadCommand;
import org.example.outgoing.repository.customer.dto.ImmutableCustomerWriteCommand;
import org.example.outgoing.repository.customer.error.CustomerReadError;
import org.example.outgoing.repository.customer.error.CustomerWriteError;
import org.example.service.command.customer.CustomerCommandServiceInteractionData;
import org.example.service.command.customer.domain.aggregate.CustomerRegister;
import org.example.service.command.customer.operation.EnsureCustomerOperationData;

public class CustomerCommandRepositoryInteraction implements
    RepositoryInteraction<CustomerCommandServiceInteractionData, CustomerData, CustomerRegister> {

  private final CustomerCommandRepository repository;

  public CustomerCommandRepositoryInteraction(CustomerCommandRepository repository) {
    this.repository = repository;
  }

  @Override
  public <T extends CustomerCommandServiceInteractionData> CompletableFuture<Result<T, Error>> read(
      final T data) {
    return repository
        .read(ImmutableCustomerReadCommand.builder().build())
        .thenApply(result -> result
            .map(d -> updateAggregateRoot(data, CustomerRegister.fromDataTransferObject(d)))
            .mapError(e -> Error.of(e.code)));
  }

  @Override
  public <T extends CustomerCommandServiceInteractionData> CompletableFuture<Result<CustomerData,
      Error>> write(
      final T data) {
    CustomerWriteCommand command = ImmutableCustomerWriteCommand
        .builder()
        .customer(data.getCustomerChange().customerData())
        .customerChange(data.getCustomerChange())
        .build();
    if (noChange(data)) {
      return CompletableFuture.completedFuture(Result.ok(data.getCustomerChange().customerData()));
    }
    return repository
        .write(command)
        .thenApply(result -> result
            .map(d -> createChangeIsApplied(data) ? data
                .getCustomerChange()
                .createCustomer()
                .customerData() : data.getCustomerChange().updateCustomer().customerData())
            .mapError(e -> Error.of(e.code)));
  }

  @Override
  public <T extends CustomerCommandServiceInteractionData> T updateAggregateRoot(
      T data, CustomerRegister aggregateRoot) {
    data.setCustomerRegister(aggregateRoot);
    return data;
  }

  @Override
  public <T extends CustomerCommandServiceInteractionData> boolean noChange(final T data) {
    return data.getCustomerChange() == null || data.getCustomerChange().createCustomer() == null
        || data.getCustomerChange().updateCustomer() == null;
  }

  public boolean createChangeIsApplied(final CustomerCommandServiceInteractionData data) {
    return data.getCustomerChange() != null && data.getCustomerChange().createCustomer() != null;
  }
}
