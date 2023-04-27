package org.example.application.customer;

import java.util.UUID;
import org.example.incoming.service.customer.CustomerQueryService;
import org.example.incoming.service.customer.ImmutableCustomerServiceQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerApplication {

  Logger logger = LoggerFactory.getLogger(CustomerApplication.class);

  private final CustomerQueryService customerQueryService;

  @Autowired
  public CustomerApplication(CustomerQueryService customerQueryService) {
    this.customerQueryService = customerQueryService;
  }

  @GetMapping("/customer/{id}")
  public void getCustomer(
      @PathVariable("id")
      String id) {
    logger.info("-----> starting");
    this.customerQueryService.getCustomerByUUID(ImmutableCustomerServiceQuery
        .builder()
        .customerIdentifier(UUID.fromString(id))
        .build());
  }
}
