package org.example.test.persona.order;

import java.util.UUID;
import org.example.dto.base.ImmutableCustomerReferenceData;
import org.example.dto.graph.CustomerData;
import org.example.dto.graph.ImmutableCustomerData;
import org.example.dto.graph.ImmutableOrderData;
import org.example.dto.graph.OrderData;
import org.example.test.data.model.CustomerTestData;
import org.example.test.data.model.OrderTestData;
import org.example.test.persona.customer.John;

public class Shoes implements OrderTestData {

  public static String IDENTIFIER = "444faf9c-e150-11ed-b5ea-0242ac120002";

  @Override
  public OrderData orderData() {
    return ImmutableOrderData
        .builder()
        .identifier(UUID.fromString(IDENTIFIER))
        .customerReferenceData(ImmutableCustomerReferenceData
            .builder()
            .customerIdentifier(UUID.fromString(John.IDENTIFIER))
            .build())
        .productName("Shoes")
        .build();
  }
}
