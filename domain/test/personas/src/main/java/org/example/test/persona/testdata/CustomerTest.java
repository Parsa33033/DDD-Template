package org.example.test.persona.testdata;

import org.example.dto.graph.CustomerData;
import org.example.test.data.model.CustomerTestData;
import org.example.test.persona.customer.John;

public enum CustomerTest implements CustomerTestData {
  John(new John());
  CustomerTestData customerTestData;

  CustomerTest(CustomerTestData customerTestData) {
    this.customerTestData = customerTestData;
  }

  @Override
  public CustomerData customerData() {
    return this.customerTestData.customerData();
  }
}
