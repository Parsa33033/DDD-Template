package org.example.test.persona.customer;

import java.util.UUID;
import org.example.dto.graph.CustomerData;
import org.example.dto.graph.ImmutableCustomerData;
import org.example.test.data.model.CustomerTestData;

public class John implements CustomerTestData {

  public static String IDENTIFIER = "588faf9c-e150-11ed-b5ea-0242ac120002";

  @Override
  public CustomerData customerData() {
    return ImmutableCustomerData
        .builder()
        .identifier(UUID.fromString(IDENTIFIER))
        .name("John")
        .build();
  }
}
