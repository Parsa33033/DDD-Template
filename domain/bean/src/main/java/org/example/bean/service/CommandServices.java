package org.example.bean.service;

import org.example.incoming.service.customer.CustomerCommandService;
import org.example.incoming.service.order.CustomerOrderCommandService;
import org.example.outgoing.repository.customer.CustomerCommandRepository;
import org.example.outgoing.repository.order.OrderCommandRepository;
import org.example.service.command.customer.CustomerCommandServiceInteraction;
import org.example.service.command.customerorder.CustomerOrderCommandServiceInteraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CommandServices {


  @Bean
  @Autowired
  public CustomerCommandService customerCommandService(CustomerCommandRepository customerCommandRepository) {
    return new CustomerCommandServiceInteraction(customerCommandRepository);
  }

  @Bean
  @Autowired
  public CustomerOrderCommandService customerOrderCommandService(OrderCommandRepository orderCommandRepository) {
    return new CustomerOrderCommandServiceInteraction(orderCommandRepository);
  }
}
