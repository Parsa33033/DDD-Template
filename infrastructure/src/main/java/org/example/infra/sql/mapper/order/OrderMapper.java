package org.example.infra.sql.mapper.order;

import java.util.UUID;
import org.example.dto.base.ImmutableCustomerReferenceData;
import org.example.dto.base.ImmutableOrderReferenceData;
import org.example.dto.graph.OrderData;
import org.example.dto.graph.ImmutableOrderData;
import org.example.dto.graph.OrderData;
import org.example.infra.sql.model.Customer;
import org.example.infra.sql.model.Order;
import org.example.infra.sql.model.Order;

public final class OrderMapper {


  public static OrderData mapToDTO(Order order) {
    return ImmutableOrderData
        .builder()
        .customerReferenceData(ImmutableCustomerReferenceData
            .builder()
            .customerIdentifier(UUID.fromString(order.getCustomer().getCustomerId()))
            .build())
        .productName(order.getProductName())
        .identifier(UUID.fromString(order.getOrderId()))
        .build();
  }

  public static Order mapToEntity(OrderData orderData) {
    Order order = new Order();
    order.setOrderId(orderData.identifier().toString());
    order.setProductName(orderData.productName());
    Customer customer = new Customer();
    customer.setCustomerId(orderData.customerReferenceData().customerIdentifier().toString());
    order.setCustomer(customer);
    return order;
  }
}
