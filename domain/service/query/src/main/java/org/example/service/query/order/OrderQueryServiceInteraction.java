package org.example.service.query.order;

import java.util.concurrent.CompletableFuture;
import org.example.dto.graph.CustomerData;
import org.example.dto.graph.OrderData;
import org.example.framework.result.Result;
import org.example.incoming.service.order.OrderQueryService;
import org.example.incoming.service.order.OrderServiceError;
import org.example.incoming.service.order.OrderServiceQuery;
import org.example.model.config.error.DomainErrorCodeMessageInitializer;
import org.example.outgoing.repository.order.ImmutableOrderQuery;
import org.example.outgoing.repository.order.OrderQueryRepository;

public class OrderQueryServiceInteraction implements OrderQueryService {

  private final OrderQueryRepository orderQueryRepository;

  public OrderQueryServiceInteraction(OrderQueryRepository orderQueryRepository) {
    domainInit();
    this.orderQueryRepository = orderQueryRepository;
  }

  @Override
  public void domainInit() {
    DomainErrorCodeMessageInitializer.init();
  }

  @Override
  public CompletableFuture<Result<OrderData, OrderServiceError>> getOrderByUUID(
      OrderServiceQuery query) {
    return orderQueryRepository
        .getOrderByUUID(ImmutableOrderQuery
            .builder()
            .orderIdentifier(query.orderIdentifier())
            .build())
        .thenApply(result -> result
            .map(r -> r)
            .mapError(e -> OrderServiceError.of(e.error.code())));
  }
}
