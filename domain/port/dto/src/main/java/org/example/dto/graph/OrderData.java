package org.example.dto.graph;

import java.util.UUID;
import org.example.dto.base.CustomerReferenceData;
import org.example.framework.dto.DataTransferObject;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

@Immutable
public interface OrderData extends DataTransferObject {

  @Nullable
  UUID identifier();


  @Nullable
  String productName();

  @Nullable
  CustomerReferenceData customerReferenceData();
}
