package org.example.infra.postgres.repository.query;

import java.util.concurrent.CompletableFuture;
import org.example.dto.graph.CustomerData;
import org.example.dto.graph.OrderData;
import org.example.framework.result.Result;
import org.example.outgoing.repository.customer.CustomerQuery;
import org.example.outgoing.repository.customer.CustomerQueryError;
import org.example.outgoing.repository.customer.CustomerQueryRepository;
import org.example.outgoing.repository.order.OrderQueryError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerQueryRepositoryPostgres implements CustomerQueryRepository {

  Logger logger = LoggerFactory.getLogger(CustomerQueryRepositoryPostgres.class);

//  private final CustomerRepository customerRepository;
//
//  public CustomerQueryRepositoryPostgres(CustomerRepository customerRepository) {
//    this.customerRepository = customerRepository;
//  }

  @Override
  public CompletableFuture<Result<CustomerData, CustomerQueryError>> getCustomerByUUID(final CustomerQuery query) {
    logger.info("--------> query for customer: {}", query.customerIdentifier());
    return null;
  }
}
