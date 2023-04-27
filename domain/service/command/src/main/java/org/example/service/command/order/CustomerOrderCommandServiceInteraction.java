package org.example.service.command.order;

import java.util.concurrent.CompletableFuture;
import org.example.dto.graph.CustomerData;
import org.example.dto.graph.OrderData;
import org.example.framework.result.Result;
import org.example.incoming.service.customer.CustomerCommandService;
import org.example.incoming.service.customer.CustomerServiceCommand;
import org.example.incoming.service.customer.CustomerServiceError;
import org.example.incoming.service.order.CustomerOrderCommandService;
import org.example.incoming.service.order.CustomerOrderServiceCommand;
import org.example.incoming.service.order.CustomerOrderServiceError;
import org.example.model.config.error.DomainErrorCodeMessageInitializer;
import org.example.outgoing.repository.customer.CustomerCommandRepository;
import org.example.outgoing.repository.order.OrderCommandRepository;
import org.example.service.command.order.operation.CustomerOrderOperation;
import org.example.service.command.order.repository.CustomerOrderCommandRepositoryInteraction;

public class CustomerOrderCommandServiceInteraction implements CustomerOrderCommandService {

  private final CustomerOrderOperation customerOrderOperation;

  public CustomerOrderCommandServiceInteraction(OrderCommandRepository customerCommandRepository) {
    domainInit();
    CustomerOrderCommandRepositoryInteraction customerOrderCommandRepositoryInteraction =
        new CustomerOrderCommandRepositoryInteraction(
        customerCommandRepository);
    customerOrderOperation = new CustomerOrderOperation(customerOrderCommandRepositoryInteraction);
  }

  @Override
  public void domainInit() {
    DomainErrorCodeMessageInitializer.init();
  }

  @Override
  public CompletableFuture<Result<OrderData, CustomerOrderServiceError>> createOrder(
      final CustomerOrderServiceCommand command) {
    return customerOrderOperation.execute(command);
  }
}
