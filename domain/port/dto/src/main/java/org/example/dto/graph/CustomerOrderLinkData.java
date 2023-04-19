package org.example.dto.graph;

import java.util.UUID;
import org.example.framework.dto.DataTransferObject;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

@Immutable
public interface CustomerOrderLinkData extends DataTransferObject {

  @Nullable
  UUID customerIdentifier();

  @Nullable
  UUID orderIdentifier();

}
