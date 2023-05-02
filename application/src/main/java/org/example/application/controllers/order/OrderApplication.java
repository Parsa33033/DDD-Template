package org.example.application.controllers.order;

import static org.example.application.mapper.ResultMapper.mapResult;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import org.example.application.dto.OrderDTO;
import org.example.application.mapper.OrderMapper;
import org.example.dto.base.ImmutableCustomerReferenceData;
import org.example.dto.graph.ImmutableOrderData;
import org.example.dto.graph.OrderData;
import org.example.framework.result.Result;
import org.example.incoming.service.order.CustomerOrderCommandService;
import org.example.incoming.service.order.CustomerOrderServiceError;
import org.example.incoming.service.order.ImmutableCustomerOrderServiceCommand;
import org.example.incoming.service.order.ImmutableOrderServiceQuery;
import org.example.incoming.service.order.OrderQueryService;
import org.example.incoming.service.order.OrderServiceError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/order")
public class OrderApplication {

  private final OrderQueryService orderQueryService;
  private final CustomerOrderCommandService customerOrderCommandService;
  private final OrderMapper mapper = new OrderMapper();

  @Autowired
  public OrderApplication(
      OrderQueryService orderQueryService,
      CustomerOrderCommandService customerOrderCommandService) {
    this.orderQueryService = orderQueryService;
    this.customerOrderCommandService = customerOrderCommandService;
  }

  /**
   *
   * @param id
   * @return
   * @throws ExecutionException
   * @throws InterruptedException
   */
  @GetMapping("/{id}")
  public OrderDTO getOrder(
      @PathVariable("id")
      String id) throws ExecutionException, InterruptedException {

    Result<OrderData, OrderServiceError> result = this.orderQueryService
        .getOrderByUUID(ImmutableOrderServiceQuery
            .builder()
            .orderIdentifier(UUID.fromString(id))
            .build())
        .get();

    return mapResult(result, mapper::mapFrom);
  }

  /**
   *
   * @param orderDTO
   * @return
   * @throws ExecutionException
   * @throws InterruptedException
   */
  @PostMapping
  public OrderDTO createOrder(
      @RequestBody
      OrderDTO orderDTO) throws ExecutionException, InterruptedException {

    Result<OrderData, CustomerOrderServiceError> result = this.customerOrderCommandService
        .createOrder(ImmutableCustomerOrderServiceCommand
            .builder()
            .order(ImmutableOrderData
                .builder()
                .productName(orderDTO.getProductName())
                .customerReferenceData(ImmutableCustomerReferenceData
                    .builder()
                    .customerIdentifier(UUID.fromString(orderDTO.getCustomerIdentifier()))
                    .build())
                .build())
            .build())
        .get();

    return mapResult(result, mapper::mapFrom);
  }

  /**
   *
   * @param orderDTO
   * @return
   * @throws ExecutionException
   * @throws InterruptedException
   */
  @PutMapping
  public OrderDTO updateOrder(
      @RequestBody
      OrderDTO orderDTO) throws ExecutionException, InterruptedException {

    Result<OrderData, CustomerOrderServiceError> result = this.customerOrderCommandService
        .createOrder(ImmutableCustomerOrderServiceCommand
            .builder()
            .order(ImmutableOrderData
                .builder()
                .identifier(UUID.fromString(orderDTO.getIdentifier()))
                .productName(orderDTO.getProductName())
                .customerReferenceData(ImmutableCustomerReferenceData
                    .builder()
                    .customerIdentifier(UUID.fromString(orderDTO.getCustomerIdentifier()))
                    .build())
                .build())
            .build())
        .get();

    return mapResult(result, mapper::mapFrom);
  }
}
