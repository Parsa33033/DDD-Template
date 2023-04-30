package org.example.infra.postgres.repository.query;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.example.dto.graph.CustomerData;
import org.example.dto.graph.OrderData;
import org.example.framework.error.Error;
import org.example.framework.result.Result;
import org.example.infra.postgres.mapper.order.CustomerMapper;
import org.example.infra.postgres.model.Customer;
import org.example.infra.postgres.repository.CustomerRepository;
import org.example.outgoing.repository.customer.CustomerQuery;
import org.example.outgoing.repository.customer.CustomerQueryError;
import org.example.outgoing.repository.customer.CustomerQueryRepository;
import org.example.outgoing.repository.order.OrderQueryError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerQueryRepositoryPostgres implements CustomerQueryRepository {

  Logger logger = LoggerFactory.getLogger(CustomerQueryRepositoryPostgres.class);

  private final CustomerRepository customerRepository;

  public CustomerQueryRepositoryPostgres(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public CompletableFuture<Result<CustomerData, CustomerQueryError>> getCustomerByUUID(final CustomerQuery query) {
    Optional<Customer> customerOptional = customerRepository.findById(query.customerIdentifier());
    return customerOptional
        .<CompletableFuture<Result<CustomerData, CustomerQueryError>>>map(customer -> CompletableFuture.completedFuture(
            Result.ok(CustomerMapper.mapToDTO(customer))))
        .orElseGet(() -> CompletableFuture.completedFuture(Result.error(CustomerQueryError.of(
            CustomerQueryError.CUSTOMER_NOT_FOUND))));
  }
}
