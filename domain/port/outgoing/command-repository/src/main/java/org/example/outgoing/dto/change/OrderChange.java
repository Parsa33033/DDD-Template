package org.example.outgoing.dto.change;

import org.example.dto.graph.OrderData;
import org.example.framework.repository.RepositoryChangeData;
import org.example.outgoing.dto.operation.customer.CreateCustomer;
import org.example.outgoing.dto.operation.customer.UpdateCustomer;
import org.example.outgoing.dto.operation.order.CreateOrder;
import org.example.outgoing.dto.operation.order.UpdateOrder;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

@Immutable
public interface OrderChange extends RepositoryChangeData {

  @Nullable
  OrderData orderData();

  @Nullable
  CreateOrder createOrder();

  @Nullable
  UpdateOrder updateOrder();
}
