package org.example.dto.aggregate;

import org.example.dto.graph.CustomerData;
import org.example.framework.dto.DataTransferObject;
import org.immutables.value.Value.Immutable;

@Immutable
public interface CustomerRegisterData extends DataTransferObject {

  CustomerData customerData();
}
