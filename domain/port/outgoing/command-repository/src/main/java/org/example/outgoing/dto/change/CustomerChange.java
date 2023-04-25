package org.example.outgoing.dto.change;

import org.example.dto.graph.CustomerData;
import org.example.framework.repository.RepositoryChangeData;
import org.example.outgoing.dto.operation.CreateCustomer;
import org.example.outgoing.dto.operation.UpdateCustomer;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

@Immutable
public interface CustomerChange extends RepositoryChangeData {

  @Nullable
  CustomerData customerData();

  @Nullable
  CreateCustomer createCustomer();

  @Nullable
  UpdateCustomer updateCustomer();
}
