package org.example.outgoing.repository.order;

import java.util.UUID;
import org.example.framework.query.Query;
import org.immutables.value.Value.Immutable;

@Immutable
public interface OrderQuery extends Query {

  UUID orderIdentifier();
}
