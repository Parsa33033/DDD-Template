package org.example.outgoing.dto.change;

import org.example.framework.repository.RepositoryChangeData;
import org.example.outgoing.dto.operation.CreateCustomer;
import org.example.outgoing.dto.operation.UpdateCustomer;
import org.immutables.value.Value.Immutable;

@Immutable
public interface CustomerChange extends RepositoryChangeData {

  CreateCustomer createCustomer();

  UpdateCustomer updateCustomer();
}
