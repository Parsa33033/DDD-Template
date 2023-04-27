package org.example.outgoing.repository.order;

import java.util.concurrent.CompletableFuture;
import org.example.dto.graph.CustomerData;
import org.example.dto.graph.OrderData;
import org.example.framework.repository.QueryRepository;
import org.example.framework.result.Result;
import org.example.outgoing.repository.customer.CustomerQuery;
import org.example.outgoing.repository.customer.CustomerQueryError;

public interface OrderQueryRepository extends QueryRepository {

  CompletableFuture<Result<OrderData, OrderQueryError>> getOrderByUUID(CustomerQuery query);
}
