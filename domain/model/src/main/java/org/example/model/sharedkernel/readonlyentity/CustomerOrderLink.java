package org.example.model.sharedkernel.readonlyentity;

import static org.example.framework.model.DomainObjectBuilder.tryGetObject;

import javax.validation.constraints.NotNull;
import org.example.dto.graph.CustomerOrderLinkData;
import org.example.dto.graph.ImmutableCustomerOrderLinkData;
import org.example.framework.model.DomainObjectBuilder;
import org.example.framework.model.ReadOnlyEntity;
import org.example.model.sharedkernel.valueobject.simple.Identifier;

public class CustomerOrderLink extends ReadOnlyEntity<CustomerOrderLink, CustomerOrderLinkData> {

  @NotNull
  private Identifier customerIdentifier;

  @NotNull
  private Identifier orderIdentifier;

  CustomerOrderLink(CustomerOrderLinkBuilder builder) {
    this.customerIdentifier = builder.customerIdentifier;
    this.orderIdentifier = builder.orderIdentifier;
  }

  @Override
  public CustomerOrderLinkData toDataTransferObject() {
    return ImmutableCustomerOrderLinkData
        .builder()
        .customerIdentifier(tryGetObject(customerIdentifier, Identifier::value))
        .orderIdentifier(tryGetObject(orderIdentifier, Identifier::value))
        .build();
  }

  public static CustomerOrderLink fromDataTransferObject(final CustomerOrderLinkData dto) {
    return new CustomerOrderLinkBuilder(dto).build();
  }

  private static class CustomerOrderLinkBuilder extends
      DomainObjectBuilder<CustomerOrderLink, CustomerOrderLinkData> {

    public Identifier customerIdentifier;
    public Identifier orderIdentifier;

    public CustomerOrderLinkBuilder(CustomerOrderLinkData data) {
      super(data);
    }

    @Override
    protected CustomerOrderLink build() {
      this.customerIdentifier = tryCreateObject(this.dto.customerIdentifier(), Identifier::create);
      this.orderIdentifier = tryCreateObject(this.dto.orderIdentifier(), Identifier::create);
      return new CustomerOrderLink(this);
    }
  }
}
