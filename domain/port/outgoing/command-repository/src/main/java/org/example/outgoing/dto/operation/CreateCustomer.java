package org.example.outgoing.dto.operation;

import org.example.dto.graph.CustomerData;
import org.example.framework.repository.RepositoryOperationData;
import org.immutables.value.Value.Immutable;

@Immutable
public interface CreateCustomer extends RepositoryOperationData {

  CustomerData customerData();
}
