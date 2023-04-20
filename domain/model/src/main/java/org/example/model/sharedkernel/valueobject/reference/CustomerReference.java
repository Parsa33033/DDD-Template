package org.example.model.sharedkernel.valueobject.reference;

import static org.example.framework.model.DomainObjectBuilder.tryGetObject;

import org.example.dto.base.CustomerReferenceData;
import org.example.dto.base.ImmutableCustomerReferenceData;
import org.example.framework.model.DomainObjectBuilder;
import org.example.framework.model.ReferenceValueObject;
import org.example.model.sharedkernel.valueobject.simple.Identifier;

public class CustomerReference implements ReferenceValueObject<CustomerReferenceData> {

  private final Identifier identifier;

  private CustomerReference(CustomerReferenceBuilder builder) {
    this.identifier = builder.identifier;
  }

  @Override
  public CustomerReferenceData toDataTransferObject() {
    return ImmutableCustomerReferenceData
        .builder()
        .customerIdentifier(tryGetObject(identifier, Identifier::value))
        .build();
  }

  public static CustomerReference fromDataTransferObject(CustomerReferenceData customerReferenceData) {
    return new CustomerReferenceBuilder(customerReferenceData).build();
  }

  private static class CustomerReferenceBuilder extends
      DomainObjectBuilder<CustomerReference, CustomerReferenceData> {

    private Identifier identifier;

    public CustomerReferenceBuilder(CustomerReferenceData customerReferenceData) {
      super(customerReferenceData);
    }

    @Override
    protected CustomerReference build() {
      identifier = tryCreateObject(this.dto.customerIdentifier(), Identifier::create);
      return new CustomerReference(this);
    }
  }
}
