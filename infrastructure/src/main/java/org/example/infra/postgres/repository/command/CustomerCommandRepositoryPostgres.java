package org.example.infra.postgres.repository.command;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.example.dto.aggregate.CustomerRegisterData;
import org.example.dto.aggregate.ImmutableCustomerRegisterData;
import org.example.dto.graph.CustomerData;
import org.example.framework.result.Nothing;
import org.example.framework.result.Result;
import org.example.infra.postgres.mapper.order.CustomerMapper;
import org.example.infra.postgres.model.Customer;
import org.example.infra.postgres.repository.CustomerRepository;
import org.example.outgoing.repository.customer.CustomerCommandRepository;
import org.example.outgoing.repository.customer.CustomerQuery;
import org.example.outgoing.repository.customer.CustomerQueryError;
import org.example.outgoing.repository.customer.CustomerQueryRepository;
import org.example.outgoing.repository.customer.dto.CustomerReadCommand;
import org.example.outgoing.repository.customer.dto.CustomerWriteCommand;
import org.example.outgoing.repository.customer.error.CustomerReadError;
import org.example.outgoing.repository.customer.error.CustomerWriteError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerCommandRepositoryPostgres implements CustomerCommandRepository {

  Logger logger = LoggerFactory.getLogger(CustomerCommandRepositoryPostgres.class);

  private final CustomerRepository customerRepository;

  public CustomerCommandRepositoryPostgres(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public CompletableFuture<Result<CustomerRegisterData, CustomerReadError>> read(final CustomerReadCommand command) {

    if (command == null || command.customerIdentifier() == null) {
      return CompletableFuture.completedFuture(Result.error(CustomerReadError.of(CustomerReadError.INVALID_REQUEST)));
    }

    Optional<Customer> customerOptional = customerRepository.findById(command.customerIdentifier().toString());

    if (customerOptional.isEmpty()) {
      return CompletableFuture.completedFuture(Result.error(CustomerReadError.of(CustomerReadError.CUSTOMER_NOT_FOUND)));
    }

    return CompletableFuture.completedFuture(Result.ok(ImmutableCustomerRegisterData
        .builder()
        .customerData(CustomerMapper.mapToDTO(customerOptional.get()))
        .build()));
  }

  @Override
  public CompletableFuture<Result<Nothing, CustomerWriteError>> write(final CustomerWriteCommand command) {
    logger.info("----> write command {}", command);
    if (command == null || (command.customerChange() == null && command.customer() == null)) {
      return CompletableFuture.completedFuture(Result.error(CustomerWriteError.of(CustomerWriteError.INVALID_REQUEST)));
    }
    if (command.customerChange() != null && command.customerChange().createCustomer() != null
        && command.customerChange().createCustomer().customerData() != null) {
      customerRepository.save(CustomerMapper.mapToEntity(command
          .customerChange()
          .createCustomer()
          .customerData()));
    } else if (command.customerChange() != null && command.customerChange().updateCustomer() != null
        && command.customerChange().updateCustomer().customerData() != null) {
      customerRepository.save(CustomerMapper.mapToEntity(command
          .customerChange()
          .updateCustomer()
          .customerData()));
    }
    return CompletableFuture.completedFuture(Result.ok(Nothing.get()));
  }
}
