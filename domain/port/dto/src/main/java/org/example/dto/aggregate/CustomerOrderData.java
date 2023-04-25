package org.example.dto.aggregate;

import java.util.Set;
import org.example.dto.graph.CustomerData;
import org.example.dto.graph.OrderData;
import org.example.framework.dto.DataTransferObject;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

@Immutable
public interface CustomerOrderData extends DataTransferObject {

  @Nullable
  CustomerData customer();

  @Nullable
  Set<OrderData> customerOrders();
}
