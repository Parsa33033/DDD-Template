package org.example.incoming.service.order;

import java.util.concurrent.CompletableFuture;
import org.example.dto.graph.CustomerData;
import org.example.dto.graph.OrderData;
import org.example.framework.result.Result;
import org.example.framework.service.QueryService;

public interface OrderQueryService extends QueryService {

  CompletableFuture<Result<OrderData, OrderServiceError>> getOrderByUUID(
      OrderServiceQuery query);
}
