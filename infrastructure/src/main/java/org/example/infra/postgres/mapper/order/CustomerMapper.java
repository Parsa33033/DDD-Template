package org.example.infra.postgres.mapper.order;

import java.util.UUID;
import org.example.dto.graph.CustomerData;
import org.example.dto.graph.ImmutableCustomerData;
import org.example.infra.postgres.model.Customer;


public final class CustomerMapper {


  public static CustomerData mapToDTO(Customer customer) {
    return ImmutableCustomerData.builder()
        .name(customer.getName())
        .identifier(UUID.fromString(customer.getCustomerId()))
        .build();
  }

  public static Customer mapToEntity(CustomerData customerData) {
    Customer customer = new Customer();
    customer.setCustomerId(customerData.identifier().toString());
    customer.setName(customerData.name());
    return customer;
  }
}
