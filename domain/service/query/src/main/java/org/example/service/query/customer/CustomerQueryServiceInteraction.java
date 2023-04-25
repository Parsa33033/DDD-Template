package org.example.service.query.customer;

import java.util.concurrent.CompletableFuture;
import org.example.dto.graph.CustomerData;
import org.example.framework.result.Result;
import org.example.incoming.service.customer.CustomerQueryService;
import org.example.incoming.service.customer.CustomerServiceError;
import org.example.incoming.service.customer.CustomerServiceQuery;
import org.example.outgoing.repository.customer.CustomerQueryRepository;
import org.example.outgoing.repository.customer.ImmutableCustomerQuery;

public class CustomerQueryServiceInteraction implements CustomerQueryService {

  private final CustomerQueryRepository customerQueryRepository;

  public CustomerQueryServiceInteraction(CustomerQueryRepository customerQueryRepository) {
    this.customerQueryRepository = customerQueryRepository;
  }

  @Override
  public CompletableFuture<Result<CustomerData, CustomerServiceError>> getCustomerByUUID(
      final CustomerServiceQuery query) {
    return customerQueryRepository
        .getCustomerByUUID(ImmutableCustomerQuery
            .builder()
            .customerIdentifier(query.customerIdentifier())
            .build())
        .thenApply(result -> result
            .map(r -> r)
            .mapError(e -> CustomerServiceError.of(e.error.code())));
  }
}
