package org.example.incoming.service.order;

import java.util.UUID;
import org.example.framework.query.Query;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

@Immutable
public interface OrderServiceQuery extends Query {

  @Nullable
  UUID orderIdentifier();
}
