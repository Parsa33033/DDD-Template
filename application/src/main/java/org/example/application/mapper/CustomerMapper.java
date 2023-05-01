package org.example.application.mapper;

import org.example.application.dto.CustomerDTO;
import org.example.dto.graph.CustomerData;

public class CustomerMapper implements ApplicationMapper<CustomerDTO, CustomerData>{

  public CustomerDTO mapFrom(CustomerData customerData) {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setIdentifier(customerData.identifier().toString());
    customerDTO.setName(customerData.name());
    return customerDTO;
  }
}
