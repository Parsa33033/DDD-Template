package org.example.outgoing.repository.order;

import java.util.UUID;
import org.example.framework.query.Query;

public interface OrderQuery extends Query {

  UUID customerIdentifier();
}
