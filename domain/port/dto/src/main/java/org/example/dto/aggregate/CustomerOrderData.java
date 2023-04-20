package org.example.dto.aggregate;

import java.util.Set;
import org.example.dto.graph.CustomerData;
import org.example.dto.graph.OrderData;
import org.example.framework.dto.DataTransferObject;
import org.immutables.value.Value.Immutable;

@Immutable
public interface CustomerOrderData extends DataTransferObject {

  CustomerData customer();

  Set<OrderData> customerOrders();
}
