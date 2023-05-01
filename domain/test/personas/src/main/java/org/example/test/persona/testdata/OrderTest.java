package org.example.test.persona.testdata;

import org.example.dto.graph.OrderData;
import org.example.test.data.model.OrderTestData;
import org.example.test.persona.order.Shoes;

public enum OrderTest implements OrderTestData {
  Shoes(new Shoes());
  OrderTestData orderTestData;

  OrderTest(OrderTestData orderTestData) {
    this.orderTestData = orderTestData;
  }


  @Override
  public OrderData orderData() {
    return orderTestData.orderData();
  }
}
