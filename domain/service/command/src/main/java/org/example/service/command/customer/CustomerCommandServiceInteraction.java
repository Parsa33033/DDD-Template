package org.example.service.command.customer;

import java.util.concurrent.CompletableFuture;
import org.example.dto.graph.CustomerData;
import org.example.framework.result.Result;
import org.example.incoming.service.customer.CustomerCommandService;
import org.example.incoming.service.customer.CustomerServiceCommand;
import org.example.incoming.service.customer.CustomerServiceError;
import org.example.model.config.error.DomainErrorCodeMessageInitializer;
import org.example.outgoing.repository.customer.CustomerCommandRepository;
import org.example.service.command.customer.operation.EnsureCustomerOperation;
import org.example.service.command.customer.repository.CustomerCommandRepositoryInteraction;

public class CustomerCommandServiceInteraction implements CustomerCommandService {

  private final EnsureCustomerOperation ensureCustomerOperation;

  public CustomerCommandServiceInteraction(CustomerCommandRepository customerCommandRepository) {
    domainInit();
    CustomerCommandRepositoryInteraction customerCommandRepositoryInteraction =
        new CustomerCommandRepositoryInteraction(
        customerCommandRepository);
    ensureCustomerOperation = new EnsureCustomerOperation(customerCommandRepositoryInteraction);
  }

  @Override
  public void domainInit() {
    DomainErrorCodeMessageInitializer.init();
  }

  @Override
  public CompletableFuture<Result<CustomerData, CustomerServiceError>> ensureCustomer(
      final CustomerServiceCommand command) {
    return ensureCustomerOperation.execute(command);
  }
}
