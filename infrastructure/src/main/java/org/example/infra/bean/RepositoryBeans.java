package org.example.infra.bean;

import org.example.infra.sql.repository.CustomerRepository;
import org.example.infra.sql.repository.OrderRepository;
import org.example.infra.sql.repository.command.CustomerCommandRepositorySql;
import org.example.infra.sql.repository.command.OrderCommandRepositorySql;
import org.example.infra.sql.repository.query.CustomerQueryRepositorySql;
import org.example.infra.sql.repository.query.OrderQueryRepositorySql;
import org.example.outgoing.repository.customer.CustomerCommandRepository;
import org.example.outgoing.repository.customer.CustomerQueryRepository;
import org.example.outgoing.repository.order.OrderCommandRepository;
import org.example.outgoing.repository.order.OrderQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RepositoryBeans {

  @Bean
  @Autowired
  public CustomerQueryRepository customerQueryRepository(CustomerRepository customerRepository) {
    return new CustomerQueryRepositorySql(customerRepository);
  }

  @Bean
  @Autowired
  public CustomerCommandRepository customerCommandRepository(CustomerRepository customerRepository) {
    return new CustomerCommandRepositorySql(customerRepository);
  }

  @Bean
  @Autowired
  public OrderCommandRepository orderCommandRepository(OrderRepository orderRepository) {
    return new OrderCommandRepositorySql(orderRepository);
  }

  @Bean
  @Autowired
  public OrderQueryRepository orderQueryRepository(OrderRepository orderRepository) {
    return new OrderQueryRepositorySql(orderRepository);
  }
}
