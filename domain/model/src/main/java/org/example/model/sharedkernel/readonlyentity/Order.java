package org.example.model.sharedkernel.readonlyentity;

import static org.example.framework.model.DomainObjectBuilder.tryGetObject;

import javax.validation.constraints.NotNull;
import org.example.dto.graph.ImmutableOrderData;
import org.example.dto.graph.OrderData;
import org.example.framework.model.DomainObjectBuilder;
import org.example.framework.model.ReadOnlyEntity;
import org.example.model.sharedkernel.valueobject.reference.CustomerReference;
import org.example.model.sharedkernel.valueobject.simple.Identifier;
import org.example.model.sharedkernel.valueobject.simple.ProductName;

public class Order extends ReadOnlyEntity<Order, OrderData> {

  @NotNull
  private final Identifier orderIdentifier;

  @NotNull
  private final ProductName productName;

  @NotNull
  private final CustomerReference customerReference;

  public Order(OrderBuilder builder) {
    this.orderIdentifier = builder.orderIdentifier;
    this.productName = builder.productName;
    this.customerReference = builder.customerReference;
  }

  @Override
  public OrderData toDataTransferObject() {
    return ImmutableOrderData
        .builder()
        .identifier(tryGetObject(orderIdentifier, Identifier::value))
        .productName(tryGetObject(productName, ProductName::value))
        .customerReferenceData(tryGetObject(
            customerReference,
            CustomerReference::toDataTransferObject))
        .build();
  }

  public static Order fromDataTransferObject(OrderData orderData) {
    return new OrderBuilder(orderData).build();
  }

  public static class OrderBuilder extends DomainObjectBuilder<Order, OrderData> {

    Identifier orderIdentifier;
    ProductName productName;
    CustomerReference customerReference;

    public OrderBuilder(OrderData orderData) {
      super(orderData);
    }

    @Override
    protected Order build() {
      this.orderIdentifier = tryCreateObject(this.dto.identifier(), Identifier::create);
      this.productName = tryCreateObject(this.dto.productName(), ProductName::create);
      this.customerReference = tryCreateObject(this.dto.customerReferenceData(),
          CustomerReference::fromDataTransferObject);
      return new Order(this);
    }
  }
}
