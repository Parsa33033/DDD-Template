package org.example.service.command.customer;

import org.example.framework.dto.InteractionData;
import org.example.outgoing.dto.change.CustomerChange;
import org.example.service.command.customer.domain.aggregate.CustomerRegister;

public class CustomerCommandServiceInteractionData implements InteractionData {

  private CustomerRegister customerRegister;

  private CustomerChange customerChange;

  public CustomerRegister getCustomerRegister() {
    return customerRegister;
  }

  public CustomerCommandServiceInteractionData setCustomerRegister(final CustomerRegister customerRegister) {
    this.customerRegister = customerRegister;
    return this;
  }

  public CustomerChange getCustomerChange() {
    return customerChange;
  }

  public CustomerCommandServiceInteractionData setCustomerChange(final CustomerChange customerChange) {
    this.customerChange = customerChange;
    return this;
  }
}
