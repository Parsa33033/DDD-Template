package org.example.outgoing.repository.customer;

import java.util.UUID;
import org.example.framework.query.Query;

public interface CustomerQuery extends Query {

  UUID customerIdentifier();
}
