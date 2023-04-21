package org.example.incoming.service.customer;

import java.util.UUID;
import org.example.framework.query.Query;
import org.immutables.value.Value.Immutable;

@Immutable
public interface CustomerServiceQuery extends Query {

  UUID customerIdentifier();
}
