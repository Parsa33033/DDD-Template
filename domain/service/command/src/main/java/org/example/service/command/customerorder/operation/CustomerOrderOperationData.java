package org.example.service.command.customerorder.operation;

import org.example.dto.graph.OrderData;
import org.example.service.command.customerorder.CustomerOrderCommandServiceInteractionData;

public class CustomerOrderOperationData extends CustomerOrderCommandServiceInteractionData {

  private OrderData orderData;

  public OrderData getOrderData() {
    return orderData;
  }

  public void setOrderData(final OrderData orderData) {
    this.orderData = orderData;
  }
}
