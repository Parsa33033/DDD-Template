package org.example.test.data.config;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.example.dto.graph.CustomerData;
import org.example.dto.graph.OrderData;

public interface TestData {

  Optional<CustomerData> getCustomerById(UUID identifier);
  Set<CustomerData> getCustomers();
  void addCustomer(CustomerData customerData);

  Optional<OrderData> getOrderById(UUID identifier);
  Set<OrderData> getOrders();
  void addOrder(OrderData orderData);
}
