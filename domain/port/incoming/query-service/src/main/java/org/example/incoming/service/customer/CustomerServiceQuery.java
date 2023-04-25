package org.example.incoming.service.customer;

import java.util.UUID;
import org.example.framework.query.Query;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

@Immutable
public interface CustomerServiceQuery extends Query {

  @Nullable
  UUID customerIdentifier();
}
