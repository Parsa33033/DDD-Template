package org.example.dto.aggregate;

import org.example.dto.graph.CustomerData;
import org.example.framework.dto.DataTransferObject;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

@Immutable
public interface CustomerRegisterData extends DataTransferObject {

  @Nullable
  CustomerData customerData();
}
