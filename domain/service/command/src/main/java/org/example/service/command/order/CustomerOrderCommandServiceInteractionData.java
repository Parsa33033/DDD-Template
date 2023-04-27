package org.example.service.command.order;

import java.util.Optional;
import java.util.UUID;
import org.example.framework.dto.InteractionData;
import org.example.model.sharedkernel.valueobject.simple.Identifier;
import org.example.outgoing.dto.change.CustomerChange;
import org.example.outgoing.dto.change.OrderChange;
import org.example.service.command.order.domain.aggregate.CustomerOrder;

public class CustomerOrderCommandServiceInteractionData implements InteractionData {

  private Optional<UUID> orderIdentifier;
  private CustomerOrder customerOrder;

  private OrderChange orderChange;

  public Optional<UUID> getOrderIdentifier() {
    return orderIdentifier;
  }

  public CustomerOrderCommandServiceInteractionData setOrderIdentifier(final Optional<UUID> orderIdentifier) {
    this.orderIdentifier = orderIdentifier;
    return this;
  }

  public CustomerOrder getCustomerOrder() {
    return customerOrder;
  }

  public CustomerOrderCommandServiceInteractionData setCustomerOrder(final CustomerOrder customerOrder) {
    this.customerOrder = customerOrder;
    return this;
  }

  public OrderChange getOrderChange() {
    return orderChange;
  }

  public CustomerOrderCommandServiceInteractionData setOrderChange(final OrderChange orderChange) {
    this.orderChange = orderChange;
    return this;
  }
}
