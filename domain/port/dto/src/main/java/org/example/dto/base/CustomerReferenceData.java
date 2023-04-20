package org.example.dto.base;

import java.util.UUID;
import org.example.framework.dto.DataTransferObject;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

@Immutable
public interface CustomerReferenceData extends DataTransferObject {

  @Nullable
  UUID customerIdentifier();
}
