package org.example.bean.service;

import org.example.incoming.service.customer.CustomerQueryService;
import org.example.outgoing.repository.customer.CustomerQueryRepository;
import org.example.service.query.customer.CustomerQueryServiceInteraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class QueryServices {

  @Bean
  @Autowired
  public CustomerQueryService customerQueryService(CustomerQueryRepository customerQueryRepository) {
    return new CustomerQueryServiceInteraction(customerQueryRepository);
  }
}
