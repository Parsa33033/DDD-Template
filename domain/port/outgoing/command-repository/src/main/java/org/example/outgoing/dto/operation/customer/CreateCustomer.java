package org.example.outgoing.dto.operation.customer;

import org.example.dto.graph.CustomerData;
import org.example.framework.repository.RepositoryOperationData;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

@Immutable
public interface CreateCustomer extends RepositoryOperationData {

  @Nullable
  CustomerData customerData();
}
