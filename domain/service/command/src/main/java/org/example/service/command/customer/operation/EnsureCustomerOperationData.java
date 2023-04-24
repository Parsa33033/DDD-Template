package org.example.service.command.customer.operation;

import org.example.dto.graph.CustomerData;
import org.example.service.command.customer.CustomerCommandServiceInteractionData;

public class EnsureCustomerOperationData extends CustomerCommandServiceInteractionData {

  private CustomerData customerData;

  public CustomerData getCustomerData() {
    return customerData;
  }

  public void setCustomerData(final CustomerData customerData) {
    this.customerData = customerData;
  }
}
