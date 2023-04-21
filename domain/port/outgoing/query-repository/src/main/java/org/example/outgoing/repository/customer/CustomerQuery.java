package org.example.outgoing.repository.customer;

import java.util.UUID;
import org.example.framework.query.Query;
import org.immutables.value.Value.Immutable;

@Immutable
public interface CustomerQuery extends Query {

  UUID customerIdentifier();
}
