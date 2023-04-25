package org.example.service.command.customer;

import java.util.Optional;
import java.util.UUID;
import org.example.framework.dto.InteractionData;
import org.example.model.sharedkernel.valueobject.simple.Identifier;
import org.example.outgoing.dto.change.CustomerChange;
import org.example.service.command.customer.domain.aggregate.CustomerRegister;

public class CustomerCommandServiceInteractionData implements InteractionData {

  private Optional<UUID> customerIdentifier;
  private CustomerRegister customerRegister;

  private CustomerChange customerChange;

  public Optional<UUID> getCustomerIdentifier() {
    return customerIdentifier;
  }

  public CustomerCommandServiceInteractionData setCustomerIdentifier(final Optional<UUID> customerIdentifier) {
    this.customerIdentifier = customerIdentifier;
    return this;
  }

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
