package org.example.application.controllers.customer;

import static org.example.application.mapper.ResultMapper.mapResult;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import org.example.application.dto.CustomerDTO;
import org.example.application.exceptions.FunctionalErrorException;
import org.example.application.mapper.CustomerMapper;
import org.example.dto.graph.CustomerData;
import org.example.dto.graph.ImmutableCustomerData;
import org.example.framework.result.Result;
import org.example.incoming.service.customer.CustomerCommandService;
import org.example.incoming.service.customer.CustomerQueryService;
import org.example.incoming.service.customer.CustomerServiceError;
import org.example.incoming.service.customer.ImmutableCustomerServiceCommand;
import org.example.incoming.service.customer.ImmutableCustomerServiceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/customer")
public class CustomerApplication {

  private final CustomerQueryService customerQueryService;
  private final CustomerCommandService customerCommandService;
  private final CustomerMapper mapper = new CustomerMapper();

  @Autowired
  public CustomerApplication(
      CustomerQueryService customerQueryService,
      CustomerCommandService customerCommandService) {
    this.customerQueryService = customerQueryService;
    this.customerCommandService = customerCommandService;
  }

  /**
   *
   * @param id
   * @return
   * @throws ExecutionException
   * @throws InterruptedException
   */
  @GetMapping("/{id}")
  public CustomerDTO getCustomer(
      @PathVariable("id")
      String id) throws ExecutionException, InterruptedException {

    Result<CustomerData, CustomerServiceError> result = this.customerQueryService.getCustomerByUUID(ImmutableCustomerServiceQuery
        .builder()
        .customerIdentifier(UUID.fromString(id))
        .build()).get();
   return mapResult(result, mapper::mapFrom);
  }

  /**
   *
   * @param customerDTO
   * @return
   * @throws ExecutionException
   * @throws InterruptedException
   */
  @PostMapping
  public CustomerDTO createCustomer(
      @RequestBody
      CustomerDTO customerDTO) throws ExecutionException, InterruptedException {

    Result<CustomerData, CustomerServiceError> result = this.customerCommandService.ensureCustomer(ImmutableCustomerServiceCommand
        .builder()
        .customer(ImmutableCustomerData.builder()
            .name(customerDTO.getName()).build())
        .build()).get();

    return mapResult(result, mapper::mapFrom);
  }

  /**
   *
   * @param customerDTO
   * @return
   * @throws ExecutionException
   * @throws InterruptedException
   */
  @PutMapping
  public CustomerDTO updateCustomer(
      @RequestBody
      CustomerDTO customerDTO) throws ExecutionException, InterruptedException {

    Result<CustomerData, CustomerServiceError> result = this.customerCommandService.ensureCustomer(ImmutableCustomerServiceCommand
        .builder()
        .customer(ImmutableCustomerData.builder()
            .identifier(UUID.fromString(customerDTO.getIdentifier()))
            .name(customerDTO.getName()).build())
        .build()).get();

    return mapResult(result, mapper::mapFrom);
  }
}
