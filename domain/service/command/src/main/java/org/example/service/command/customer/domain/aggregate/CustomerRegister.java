package org.example.service.command.customer.domain.aggregate;

import static org.example.framework.model.DomainObjectBuilder.tryGetObject;

import javax.validation.constraints.NotNull;
import org.example.dto.aggregate.CustomerRegisterData;
import org.example.dto.aggregate.ImmutableCustomerRegisterData;
import org.example.dto.graph.CustomerData;
import org.example.framework.aggregate.AggregateRoot;
import org.example.framework.model.DomainObjectBuilder;
import org.example.framework.result.Result;
import org.example.model.sharedkernel.readonlyentity.Customer;
import org.example.outgoing.dto.change.CustomerChange;
import org.example.outgoing.dto.change.ImmutableCustomerChange;
import org.example.outgoing.dto.operation.customer.ImmutableCreateCustomer;
import org.example.outgoing.dto.operation.customer.ImmutableUpdateCustomer;
import org.example.service.command.customer.domain.error.CustomerRegisterError;

public class CustomerRegister implements AggregateRoot<CustomerRegister, CustomerRegisterData> {

  @NotNull
  private Customer customer;

  public CustomerRegister(CustomerRegisterBuilder builder) {
    this.customer = builder.customer;
  }

  public Result<CustomerChange, CustomerRegisterError> ensureCustomerIsRegistered(CustomerData customerData) {

    if (customerAlreadyExists(customerData)) {
      if (customerDoesNotChange(customerData)) {
        return Result.error(CustomerRegisterError.of(CustomerRegisterError.CUSTOMER_ALREADY_EXISTS));
      }
      return Result.ok(ImmutableCustomerChange
          .builder()
          .customerData(this.customer.toDataTransferObject())
          .updateCustomer(ImmutableUpdateCustomer.builder().customerData(customerData).build())
          .build());
    }
    return Result.ok(ImmutableCustomerChange
        .builder()
        .createCustomer(ImmutableCreateCustomer.builder().customerData(customerData).build())
        .build());
  }

  public boolean customerAlreadyExists(CustomerData customerData) {
    return customer != null && customerData
        .identifier()
        .equals(customer.toDataTransferObject().identifier());
  }

  public boolean customerDoesNotChange(CustomerData customerData) {
    return customerData.equals(customer.toDataTransferObject());
  }

  @Override
  public CustomerRegisterData toDataTransferObject() {
    return ImmutableCustomerRegisterData
        .builder()
        .customerData(tryGetObject(this.customer, Customer::toDataTransferObject))
        .build();
  }

  public static CustomerRegister fromDataTransferObject(CustomerRegisterData customerRegisterData) {
    return new CustomerRegisterBuilder(customerRegisterData).build();
  }

  private static class CustomerRegisterBuilder extends
      DomainObjectBuilder<CustomerRegister, CustomerRegisterData> {

    private Customer customer;

    public CustomerRegisterBuilder(CustomerRegisterData customerRegisterData) {
      super(customerRegisterData);
    }

    @Override
    protected CustomerRegister build() {
      this.customer = tryCreateObject(this.dto.customerData(), Customer::fromDataTransferObject);
      return new CustomerRegister(this);
    }
  }
}
