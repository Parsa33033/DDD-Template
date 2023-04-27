package org.example.service.command.order.domain.aggregate;

import static org.example.framework.model.DomainObjectBuilder.tryGetObject;
import static org.example.framework.model.DomainObjectBuilder.tryGetObjects;

import java.util.Set;
import javax.validation.constraints.NotNull;
import org.example.dto.aggregate.CustomerOrderData;
import org.example.dto.aggregate.ImmutableCustomerOrderData;
import org.example.dto.graph.CustomerData;
import org.example.dto.graph.OrderData;
import org.example.framework.aggregate.AggregateRoot;
import org.example.framework.model.DomainObjectBuilder;
import org.example.framework.result.Result;
import org.example.incoming.service.order.CustomerOrderServiceError;
import org.example.model.sharedkernel.readonlyentity.Customer;
import org.example.model.sharedkernel.readonlyentity.Order;
import org.example.outgoing.dto.change.ImmutableCustomerChange;
import org.example.outgoing.dto.change.ImmutableOrderChange;
import org.example.outgoing.dto.change.OrderChange;
import org.example.outgoing.dto.operation.order.ImmutableCreateOrder;
import org.example.service.command.order.domain.error.CustomerOrderError;

public class CustomerOrder implements AggregateRoot<CustomerOrder, CustomerOrderData> {

  @NotNull
  private Customer customer;

  private Set<Order> orders;

  public CustomerOrder(CustomerOrderBuilder builder) {
    this.customer = builder.customer;
    this.orders = builder.orders;
  }

  public Result<OrderChange, CustomerOrderError> createOrder(OrderData orderData) {

    if (orderAlreadyExists(orderData)) {
      return Result.error(CustomerOrderError.of(CustomerOrderError.ORDER_ALREADY_EXISTS));
    }
    return Result.ok(ImmutableOrderChange
        .builder()
        .createOrder(ImmutableCreateOrder.builder().orderData(orderData).build())
        .build());
  }

  public boolean orderAlreadyExists(OrderData orderData) {
    return orders != null && orders
        .stream()
        .anyMatch(order -> order.toDataTransferObject().identifier().equals(orderData.identifier()));
  }

  @Override
  public CustomerOrderData toDataTransferObject() {
    return ImmutableCustomerOrderData
        .builder()
        .customer(tryGetObject(this.customer, Customer::toDataTransferObject))
        .customerOrders(tryGetObjects(this.orders, Order::toDataTransferObject))
        .build();
  }

  public static CustomerOrder fromDataTransferObject(CustomerOrderData customerOrderData) {
    return new CustomerOrderBuilder(customerOrderData).build();
  }

  private static class CustomerOrderBuilder extends
      DomainObjectBuilder<CustomerOrder, CustomerOrderData> {

    private Customer customer;
    private Set<Order> orders;

    public CustomerOrderBuilder(CustomerOrderData customerOrderData) {
      super(customerOrderData);
    }

    @Override
    protected CustomerOrder build() {
      this.customer = tryCreateObject(this.dto.customer(), Customer::fromDataTransferObject);
      this.orders = tryCreateObjects(this.dto.customerOrders(), Order::fromDataTransferObject);
      return new CustomerOrder(this);
    }
  }
}
