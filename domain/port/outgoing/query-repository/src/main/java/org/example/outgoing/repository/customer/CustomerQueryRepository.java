package org.example.outgoing.repository.customer;

import java.util.concurrent.CompletableFuture;
import org.example.dto.graph.CustomerData;
import org.example.framework.repository.QueryRepository;
import org.example.framework.result.Result;

public interface CustomerQueryRepository extends QueryRepository {

  CompletableFuture<Result<CustomerData, CustomerQueryError>> getCustomerByUUID(CustomerQuery query);
}
