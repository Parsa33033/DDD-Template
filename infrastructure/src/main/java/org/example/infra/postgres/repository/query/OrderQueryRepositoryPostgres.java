//package org.example.infra.postgres.repository.query;
//
//import java.util.concurrent.CompletableFuture;
//import org.example.dto.graph.OrderData;
//import org.example.framework.result.Result;
//import org.example.infra.postgres.mapper.order.OrderMapper;
//import org.example.infra.postgres.repository.OrderRepository;
//import org.example.outgoing.repository.customer.CustomerQuery;
//import org.example.outgoing.repository.order.OrderQueryError;
//import org.example.outgoing.repository.order.OrderQueryRepository;
//
//public class OrderQueryRepositoryPostgres implements OrderQueryRepository {
//
//  private final OrderRepository orderRepository;
//
//  public OrderQueryRepositoryPostgres(OrderRepository orderRepository) {
//    this.orderRepository = orderRepository;
//  }
//
//  @Override
//  public CompletableFuture<Result<OrderData, OrderQueryError>> getOrderByUUID(final CustomerQuery query) {
//    return CompletableFuture.completedFuture(Result.ok(OrderMapper.mapToDTO(orderRepository.getReferenceById(
//        query.customerIdentifier()))));
//  }
//}
