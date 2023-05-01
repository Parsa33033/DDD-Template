package org.example.infra.sql.repository.query;

import java.util.concurrent.CompletableFuture;
import org.example.dto.graph.OrderData;
import org.example.framework.result.Result;
import org.example.infra.sql.mapper.order.OrderMapper;
import org.example.infra.sql.repository.OrderRepository;
import org.example.outgoing.repository.order.OrderQuery;
import org.example.outgoing.repository.order.OrderQueryError;
import org.example.outgoing.repository.order.OrderQueryRepository;

public class OrderQueryRepositorySql implements OrderQueryRepository {

  private final OrderRepository orderRepository;

  public OrderQueryRepositorySql(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Override
  public CompletableFuture<Result<OrderData, OrderQueryError>> getOrderByUUID(final OrderQuery query) {
    return CompletableFuture.completedFuture(Result.ok(OrderMapper.mapToDTO(orderRepository.getReferenceById(
        query.orderIdentifier().toString()))));
  }
}
