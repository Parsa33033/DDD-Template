package org.example.application.customer;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import org.example.application.dto.CustomerDTO;
import org.example.dto.graph.CustomerData;
import org.example.dto.graph.ImmutableCustomerData;
import org.example.framework.result.Result;
import org.example.incoming.service.customer.CustomerCommandService;
import org.example.incoming.service.customer.CustomerQueryService;
import org.example.incoming.service.customer.CustomerServiceError;
import org.example.incoming.service.customer.ImmutableCustomerServiceCommand;
import org.example.incoming.service.customer.ImmutableCustomerServiceQuery;
import org.example.outgoing.repository.customer.CustomerQueryError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerApplication {

  Logger logger = LoggerFactory.getLogger(CustomerApplication.class);

  private final CustomerQueryService customerQueryService;
  private final CustomerCommandService customerCommandService;

  @Autowired
  public CustomerApplication(
      CustomerQueryService customerQueryService,
      CustomerCommandService customerCommandService) {
    this.customerQueryService = customerQueryService;
    this.customerCommandService = customerCommandService;
  }

  @GetMapping("/customer/{id}")
  public CustomerDTO getCustomer(
      @PathVariable("id")
      String id) throws ExecutionException, InterruptedException {
    logger.info("-----> reading");
    Result<CustomerData, CustomerServiceError> result =  this.customerQueryService.getCustomerByUUID(ImmutableCustomerServiceQuery
        .builder()
        .customerIdentifier(UUID.fromString(id))
        .build()).get();
    if (result.isOk()) {
      CustomerDTO customerDTO = new CustomerDTO();
      customerDTO.setIdentifier(result.object().identifier().toString());
      customerDTO.setName(result.object().name());
      return customerDTO;
    }
    throw new IllegalStateException("sodjf");
  }

  @PostMapping("/customer")
  public void createCustomer(
      @RequestBody
      CustomerDTO customerDTO) {
    logger.info("-----> creating");
    this.customerCommandService.ensureCustomer(ImmutableCustomerServiceCommand
        .builder()
        .customer(ImmutableCustomerData.builder()
            .name(customerDTO.getName()).build())
        .build());
  }

}
