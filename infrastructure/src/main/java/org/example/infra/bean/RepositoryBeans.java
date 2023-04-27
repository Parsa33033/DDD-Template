package org.example.infra.bean;

import org.example.infra.postgres.repository.query.CustomerQueryRepositoryPostgres;
import org.example.outgoing.repository.customer.CustomerQueryRepository;
import org.example.outgoing.repository.order.OrderQueryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RepositoryBeans {

  @Bean
  public CustomerQueryRepository customerQueryRepository() {
    return new CustomerQueryRepositoryPostgres();
  }

//  @Bean
//  public OrderQueryRepository customerQueryRepository(OrderRepository orderRepository) {
//    return new OrderQueryRepositoryPostgres(orderRepository);
//  }
}
