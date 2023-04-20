package org.example.model.sharedkernel.readonlyentity;

import static org.example.framework.model.DomainObjectBuilder.tryGetObject;

import javax.validation.constraints.NotNull;
import org.example.dto.graph.CustomerData;
import org.example.dto.graph.ImmutableCustomerData;
import org.example.framework.model.DomainObjectBuilder;
import org.example.framework.model.ReadOnlyEntity;
import org.example.model.sharedkernel.valueobject.simple.CustomerName;
import org.example.model.sharedkernel.valueobject.simple.Identifier;

public class Customer extends ReadOnlyEntity<Customer, CustomerData> {

  @NotNull
  private Identifier customerIdentifier;

  @NotNull
  private CustomerName customerName;


  public Customer(CustomerBuilder builder) {
    this.customerIdentifier = builder.customerIdentifier;
    this.customerName = builder.customerName;
  }

  @Override
  public CustomerData toDataTransferObject() {
    return ImmutableCustomerData.builder()
        .identifier(tryGetObject(customerIdentifier, Identifier::value))
        .name(tryGetObject(customerName, CustomerName::value))
        .build();
  }

  public Customer fromDataTransferObject(CustomerData customerData) {
    return new CustomerBuilder(customerData).build();
  }

  public static class CustomerBuilder extends DomainObjectBuilder<Customer, CustomerData> {

    Identifier customerIdentifier;

    CustomerName customerName;

    public CustomerBuilder(CustomerData customerData) {
      super(customerData);
    }

    @Override
    protected Customer build() {
      this.customerIdentifier = tryCreateObject(this.dto.identifier(), Identifier::create);
      this.customerName = tryCreateObject(this.dto.name(), CustomerName::create);
      return new Customer(this);
    }
  }
}
