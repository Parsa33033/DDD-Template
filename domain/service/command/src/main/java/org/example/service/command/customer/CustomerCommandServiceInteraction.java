package org.example.service.command.customer;

import java.util.concurrent.CompletableFuture;
import org.example.framework.result.Nothing;
import org.example.framework.result.Result;
import org.example.incoming.service.customer.CustomerCommandService;
import org.example.incoming.service.customer.CustomerServiceCommand;
import org.example.incoming.service.customer.CustomerServiceError;

public class CustomerCommandServiceInteraction implements CustomerCommandService {

  @Override
  public CompletableFuture<Result<Nothing, CustomerServiceError>> createCustomer(
      final CustomerServiceCommand command) {
    return null;
  }
}
