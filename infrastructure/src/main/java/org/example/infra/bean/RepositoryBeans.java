package org.example.infra.bean;

import org.example.infra.postgres.repository.CustomerRepository;
import org.example.infra.postgres.repository.OrderRepository;
import org.example.infra.postgres.repository.command.CustomerCommandRepositoryPostgres;
import org.example.infra.postgres.repository.query.CustomerQueryRepositoryPostgres;
import org.example.infra.postgres.repository.query.OrderQueryRepositoryPostgres;
import org.example.outgoing.repository.customer.CustomerCommandRepository;
import org.example.outgoing.repository.customer.CustomerQueryRepository;
import org.example.outgoing.repository.order.OrderQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RepositoryBeans {

  @Bean
  @Autowired
  public CustomerQueryRepository customerQueryRepository(CustomerRepository customerRepository) {
    return new CustomerQueryRepositoryPostgres(customerRepository);
  }

  @Bean
  @Autowired
  public CustomerCommandRepository customerCommandRepository(CustomerRepository customerRepository) {
    return new CustomerCommandRepositoryPostgres(customerRepository);
  }

//  @Bean
//  @Autowired
//  public OrderQueryRepository customerQueryRepository(OrderRepository orderRepository) {
//    return new OrderQueryRepositoryPostgres(orderRepository);
//  }
}
