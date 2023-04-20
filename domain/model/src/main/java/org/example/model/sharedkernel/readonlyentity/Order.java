package org.example.model.sharedkernel.readonlyentity;

import static org.example.framework.model.DomainObjectBuilder.tryGetObject;

import javax.validation.constraints.NotNull;
import org.example.dto.graph.ImmutableOrderData;
import org.example.dto.graph.OrderData;
import org.example.framework.model.DomainObjectBuilder;
import org.example.framework.model.ReadOnlyEntity;
import org.example.model.sharedkernel.valueobject.simple.Identifier;
import org.example.model.sharedkernel.valueobject.simple.ProductName;

public class Order extends ReadOnlyEntity<Order, OrderData> {

  @NotNull
  private Identifier orderIdentifier;

  @NotNull
  private ProductName productName;


  public Order(OrderBuilder builder) {
    this.orderIdentifier = builder.orderIdentifier;
    this.productName = builder.productName;
  }

  @Override
  public OrderData toDataTransferObject() {
    return ImmutableOrderData.builder()
        .identifier(tryGetObject(orderIdentifier, Identifier::value))
        .productName(tryGetObject(productName, ProductName::value))
        .build();
  }

  public Order fromDataTransferObject(OrderData orderData) {
    return new OrderBuilder(orderData).build();
  }

  public static class OrderBuilder extends DomainObjectBuilder<Order, OrderData> {

    Identifier orderIdentifier;

    ProductName productName;

    public OrderBuilder(OrderData orderData) {
      super(orderData);
    }

    @Override
    protected Order build() {
      this.orderIdentifier = tryCreateObject(this.dto.identifier(), Identifier::create);
      this.productName = tryCreateObject(this.dto.productName(), ProductName::create);
      return new Order(this);
    }
  }
}
