package org.example.incoming.service.customer;

import java.util.concurrent.CompletableFuture;
import org.example.dto.graph.CustomerData;
import org.example.framework.result.Result;
import org.example.framework.service.QueryService;

public interface CustomerQueryService extends QueryService {

  CompletableFuture<Result<CustomerData, CustomerServiceError>> getCustomerByUUID(
      CustomerServiceQuery query);
}
