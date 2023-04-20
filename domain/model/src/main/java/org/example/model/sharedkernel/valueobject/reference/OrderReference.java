package org.example.model.sharedkernel.valueobject.reference;

import static org.example.framework.model.DomainObjectBuilder.tryGetObject;

import org.example.dto.base.ImmutableOrderReferenceData;
import org.example.dto.base.OrderReferenceData;
import org.example.framework.model.DomainObjectBuilder;
import org.example.framework.model.ReferenceValueObject;
import org.example.model.sharedkernel.valueobject.simple.Identifier;

public class OrderReference implements ReferenceValueObject<OrderReferenceData> {

  private final Identifier identifier;

  private OrderReference(OrderReferenceBuilder builder) {
    this.identifier = builder.identifier;
  }

  @Override
  public OrderReferenceData toDataTransferObject() {
    return ImmutableOrderReferenceData
        .builder()
        .orderIdentifier(tryGetObject(identifier, Identifier::value))
        .build();
  }

  public static OrderReference fromDataTransferObject(OrderReferenceData orderReferenceData) {
    return new OrderReferenceBuilder(orderReferenceData).build();
  }

  private static class OrderReferenceBuilder extends
      DomainObjectBuilder<OrderReference, OrderReferenceData> {

    private Identifier identifier;

    public OrderReferenceBuilder(OrderReferenceData orderReferenceData) {
      super(orderReferenceData);
    }

    @Override
    protected OrderReference build() {
      identifier = tryCreateObject(this.dto.orderIdentifier(), Identifier::create);
      return new OrderReference(this);
    }
  }
}
