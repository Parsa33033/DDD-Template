package org.example.infra.postgres.mapper.order;

import java.util.UUID;
import org.example.dto.base.ImmutableCustomerReferenceData;
import org.example.dto.graph.ImmutableOrderData;
import org.example.dto.graph.OrderData;
import org.example.infra.postgres.model.Order;

public final class OrderMapper {


  public static OrderData mapToDTO(Order order) {
    return ImmutableOrderData
        .builder()
        .customerReferenceData(ImmutableCustomerReferenceData
            .builder()
            .customerIdentifier(order.getCustomer().getCustomerId())
            .build())
        .productName(order.getProductName())
        .identifier(order.getOrderId())
        .build();
  }
}
