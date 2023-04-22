package org.example.test.data.config;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.example.dto.graph.CustomerData;
import org.example.dto.graph.OrderData;

public class TestDataInMemory implements TestData {

  private final Set<CustomerData> customerData;
  private final Set<OrderData> orderData;

  public TestDataInMemory() {
    customerData = new HashSet<>();
    orderData = new HashSet<>();
  }

  @Override
  public Optional<CustomerData> getCustomerById(final UUID identifier) {
    return this.customerData
        .stream()
        .filter(customer -> customer.identifier().equals(identifier))
        .findAny();
  }

  @Override
  public Set<CustomerData> getCustomers() {
    return this.customerData;
  }

  @Override
  public void addCustomer(final CustomerData customerData) {
    this.customerData.add(customerData);
  }

  @Override
  public Optional<OrderData> getOrderById(final UUID identifier) {
    return this.orderData.stream().filter(order -> order.identifier().equals(identifier)).findAny();
  }

  @Override
  public Set<OrderData> getOrders() {
    return this.orderData;
  }

  @Override
  public void addOrder(final OrderData orderData) {
    this.orderData.add(orderData);
  }
}
