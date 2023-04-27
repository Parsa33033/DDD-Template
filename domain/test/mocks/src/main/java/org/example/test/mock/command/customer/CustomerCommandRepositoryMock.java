package org.example.test.mock.command.customer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.example.dto.aggregate.CustomerRegisterData;
import org.example.dto.graph.CustomerData;
import org.example.framework.result.Nothing;
import org.example.framework.result.Result;
import org.example.model.sharedkernel.readonlyentity.Customer;
import org.example.outgoing.repository.customer.CustomerCommandRepository;
import org.example.outgoing.repository.customer.CustomerQuery;
import org.example.outgoing.repository.customer.CustomerQueryError;
import org.example.outgoing.repository.customer.CustomerQueryRepository;
import org.example.outgoing.repository.customer.dto.CustomerReadCommand;
import org.example.outgoing.repository.customer.dto.CustomerWriteCommand;
import org.example.outgoing.repository.customer.error.CustomerReadError;
import org.example.outgoing.repository.customer.error.CustomerWriteError;
import org.example.test.mock.config.AggregateCommandRepositoryMock;
import org.example.test.mock.config.RepositoryMock;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;

public class CustomerCommandRepositoryMock implements
    AggregateCommandRepositoryMock<CustomerCommandRepository, CustomerReadCommand,
        CustomerRegisterData, CustomerReadError, CustomerWriteCommand, Nothing,
        CustomerWriteError> {

  private final CustomerCommandRepository customerCommandRepository;

  private final ArgumentCaptor<CustomerWriteCommand> customerWriteCommandArgumentCaptor;

  public CustomerCommandRepositoryMock() {
    customerCommandRepository = Mockito.mock(CustomerCommandRepository.class);
    customerWriteCommandArgumentCaptor = ArgumentCaptor.forClass(CustomerWriteCommand.class);
  }

  @Override
  public CustomerCommandRepository repository() {
    return customerCommandRepository;
  }

  @Override
  public CustomerWriteCommand captureWriteCommand(final UUID identifier) {
    Objects.requireNonNull(identifier, "identifier should not be null");
    return customerWriteCommandArgumentCaptor
        .getAllValues()
        .stream()
        .filter(wc -> wc.customer() == null || identifier.equals(wc.customer().identifier()))
        .filter(wc -> wc.customerChange().createCustomer() == null || identifier.equals(wc
            .customerChange()
            .createCustomer().customerData().identifier()))
        .filter(wc -> wc.customerChange().updateCustomer() == null || identifier.equals(wc
            .customerChange()
            .updateCustomer().customerData().identifier()))
        .findAny()
        .orElse(null);
  }

  @Override
  public List<CustomerWriteCommand> captureAllWriteCommands() {
    return customerWriteCommandArgumentCaptor.getAllValues();
  }

  @Override
  public void mockGetResult(
      final UUID identifier, final Result<CustomerRegisterData, CustomerReadError> result) {
    Objects.requireNonNull(identifier, "identifier should not be null");
    Objects.requireNonNull(result, "result should not be null");
    doReturn(CompletableFuture.completedFuture(result))
        .when(customerCommandRepository)
        .read(argThat(new FindByUUIDMatcher(identifier)));
  }

  @Override
  public void mockGetResult(
      final ArgumentMatcher<CustomerReadCommand> matcher,
      final Result<CustomerRegisterData, CustomerReadError> result) {
    Objects.requireNonNull(matcher, "matcher should not be null");
    Objects.requireNonNull(result, "result should not be null");
    doReturn(CompletableFuture.completedFuture(result))
        .when(customerCommandRepository)
        .read(argThat(matcher));
  }

  @Override
  public void mockAnyGetResult(final Result<CustomerRegisterData, CustomerReadError> result) {
    Objects.requireNonNull(result, "result should not be null");
    doReturn(CompletableFuture.completedFuture(result)).when(customerCommandRepository).read(any());
  }

  @Override
  public void mockAnyGetThrows(final Throwable throwable) {
    doReturn(new CompletableFuture<>().completeExceptionally(throwable))
        .when(customerCommandRepository)
        .read(any());
  }

  @Override
  public void mockAnyWriteResult(
      final ArgumentMatcher<CustomerWriteCommand> matcher,
      final Result<Nothing, CustomerWriteError> result) {
    Objects.requireNonNull(result, "result should not be null for write");
    doReturn(CompletableFuture.completedFuture(result))
        .when(customerCommandRepository)
        .write(any());
  }

  @Override
  public void mockAnyWriteResult(final Result<Nothing, CustomerWriteError> result) {
    Objects.requireNonNull(result, "result should not be null for write");
    doReturn(CompletableFuture.completedFuture(result))
        .when(customerCommandRepository)
        .write(customerWriteCommandArgumentCaptor.capture());
  }

  private static class FindByUUIDMatcher implements ArgumentMatcher<CustomerReadCommand> {

    private final UUID identifier;

    FindByUUIDMatcher(UUID uuid) {
      this.identifier = uuid;
    }

    @Override
    public boolean matches(final CustomerReadCommand customerReadCommand) {
      return customerReadCommand.customerIdentifier() != null && customerReadCommand
          .customerIdentifier()
          .equals(identifier);
    }
  }
}
