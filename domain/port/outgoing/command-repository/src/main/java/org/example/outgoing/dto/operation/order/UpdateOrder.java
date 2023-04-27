package org.example.outgoing.dto.operation.order;

import org.example.dto.graph.CustomerData;
import org.example.dto.graph.OrderData;
import org.example.framework.repository.RepositoryOperationData;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

@Immutable
public interface UpdateOrder extends RepositoryOperationData {

  @Nullable
  OrderData orderData();
}
