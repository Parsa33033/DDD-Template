//package org.example.infra.postgres.mapper.order;
//
//import org.example.dto.graph.CustomerData;
//import org.example.dto.graph.ImmutableCustomerData;
//import org.example.infra.postgres.model.Customer;
//
//
//public final class CustomerMapper {
//
//
//  public static CustomerData mapToDTO(Customer customer) {
//    return ImmutableCustomerData.builder()
//        .name(customer.getName())
//        .identifier(customer.getUuid())
//        .build();
//  }
//}
