package org.example.application.mapper;

import org.example.application.dto.OrderDTO;
import org.example.dto.graph.OrderData;

public class OrderMapper implements ApplicationMapper<OrderDTO, OrderData> {

  public OrderDTO mapFrom(OrderData orderData) {
    OrderDTO orderDTO = new OrderDTO();
    orderDTO.setIdentifier(orderData.identifier().toString());
    orderDTO.setProductName(orderData.productName());
    orderDTO.setCustomerIdentifier(orderData.customerReferenceData().customerIdentifier().toString());
    return orderDTO;
  }
}
