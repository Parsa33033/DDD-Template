package org.example.infra.sql.repository.query;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.example.dto.graph.CustomerData;
import org.example.framework.result.Result;
import org.example.infra.sql.mapper.order.CustomerMapper;
import org.example.infra.sql.model.Customer;
import org.example.infra.sql.repository.CustomerRepository;
import org.example.outgoing.repository.customer.CustomerQuery;
import org.example.outgoing.repository.customer.CustomerQueryError;
import org.example.outgoing.repository.customer.CustomerQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerQueryRepositorySql implements CustomerQueryRepository {

  Logger logger = LoggerFactory.getLogger(CustomerQueryRepositorySql.class);

  private final CustomerRepository customerRepository;

  public CustomerQueryRepositorySql(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public CompletableFuture<Result<CustomerData, CustomerQueryError>> getCustomerByUUID(final CustomerQuery query) {
    Optional<Customer> customerOptional = customerRepository.findById(query.customerIdentifier().toString());
    return customerOptional
        .<CompletableFuture<Result<CustomerData, CustomerQueryError>>>map(customer -> CompletableFuture.completedFuture(
            Result.ok(CustomerMapper.mapToDTO(customer))))
        .orElseGet(() -> CompletableFuture.completedFuture(Result.error(CustomerQueryError.of(
            CustomerQueryError.CUSTOMER_NOT_FOUND))));
  }
}
