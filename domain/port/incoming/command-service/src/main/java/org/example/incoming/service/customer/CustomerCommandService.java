package org.example.incoming.service.customer;

import java.util.concurrent.CompletableFuture;
import org.example.dto.graph.CustomerData;
import org.example.framework.result.Nothing;
import org.example.framework.result.Result;
import org.example.framework.service.CommandService;

public interface CustomerCommandService extends CommandService {

  CompletableFuture<Result<CustomerData, CustomerServiceError>> createCustomer(CustomerServiceCommand command);
}
