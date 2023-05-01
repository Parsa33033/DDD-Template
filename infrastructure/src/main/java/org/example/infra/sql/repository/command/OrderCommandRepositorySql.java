package org.example.infra.sql.repository.command;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import org.example.dto.aggregate.CustomerOrderData;
import org.example.dto.aggregate.ImmutableCustomerOrderData;
import org.example.framework.result.Nothing;
import org.example.framework.result.Result;
import org.example.infra.sql.mapper.order.CustomerMapper;
import org.example.infra.sql.mapper.order.OrderMapper;
import org.example.infra.sql.model.Order;
import org.example.infra.sql.repository.OrderRepository;
import org.example.outgoing.repository.order.OrderCommandRepository;
import org.example.outgoing.repository.order.dto.OrderReadCommand;
import org.example.outgoing.repository.order.dto.OrderWriteCommand;
import org.example.outgoing.repository.order.error.OrderReadError;
import org.example.outgoing.repository.order.error.OrderWriteError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderCommandRepositorySql implements OrderCommandRepository {

  Logger logger = LoggerFactory.getLogger(OrderCommandRepositorySql.class);

  private final OrderRepository orderRepository;

  public OrderCommandRepositorySql(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Override
  public CompletableFuture<Result<CustomerOrderData, OrderReadError>> read(final OrderReadCommand command) {

    if (command == null || command.orderIdentifier() == null) {
      return CompletableFuture.completedFuture(Result.error(OrderReadError.of(OrderReadError.INVALID_REQUEST)));
    }

    Optional<Order> orderOptional = orderRepository.findById(command.orderIdentifier().toString());

    if (orderOptional.isEmpty()) {
      return CompletableFuture.completedFuture(Result.error(OrderReadError.of(OrderReadError.ORDER_NOT_FOUND)));
    }

    return CompletableFuture.completedFuture(Result.ok(ImmutableCustomerOrderData.builder().customer(
        CustomerMapper.mapToDTO(orderOptional.get().getCustomer())).customerOrders(Set.of(OrderMapper.mapToDTO(orderOptional.get()))).build()));
  }

  @Override
  public CompletableFuture<Result<Nothing, OrderWriteError>> write(final OrderWriteCommand command) {
    logger.info("----> write command {}", command);
    if (command == null || (command.orderChange() == null && command.order() == null)) {
      return CompletableFuture.completedFuture(Result.error(OrderWriteError.of(OrderWriteError.INVALID_REQUEST)));
    }
    if (command.orderChange() != null && command.orderChange().createOrder() != null
        && command.orderChange().createOrder().orderData() != null) {
      orderRepository.save(OrderMapper.mapToEntity(command
          .orderChange()
          .createOrder()
          .orderData()));
    } else if (command.orderChange() != null && command.orderChange().updateOrder() != null
        && command.orderChange().updateOrder().orderData() != null) {
      orderRepository.save(OrderMapper.mapToEntity(command
          .orderChange()
          .updateOrder()
          .orderData()));
    }
    return CompletableFuture.completedFuture(Result.ok(Nothing.get()));
  }
}
