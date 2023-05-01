package org.example.test.steps.stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import java.util.Map;
import java.util.UUID;
import org.example.dto.base.ImmutableCustomerReferenceData;
import org.example.dto.graph.ImmutableOrderData;
import org.example.dto.graph.OrderData;
import org.example.test.data.config.TestContext;
import org.example.test.data.model.OrderTestData;
import org.example.test.persona.testdata.CustomerTest;
import org.example.test.persona.testdata.OrderTest;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderStepDefs {

  TestContext testContext;

  @Autowired
  public OrderStepDefs(TestContext testContext) {
    this.testContext = testContext;
  }

  @Given("order {orderTest}")
  public void givenOrder(OrderTest orderTest) {
    testContext.testData().addOrder(orderTest.orderData());
  }

  public static OrderData createOrderData(DataTable dataTable) {
    Map<String, String> dataMap = dataTable.asMaps().get(0);
    UUID customerIdentifier = UUID.fromString(dataMap.get("order_identifier"));
    String customerName = dataMap.get("product_name");
    CustomerTest customerTestData = CustomerTest.valueOf(dataMap.get("customer"));
    OrderData order = new OrderTestBuilder(customerIdentifier,
        customerName,
        customerTestData.customerData().identifier()).orderData();
    return order;
  }

  private static class OrderTestBuilder implements OrderTestData {

    UUID identifier;
    String productName;

    UUID customerIdentifier;

    public OrderTestBuilder(UUID identifier, String productName, UUID customerIdentifier) {
      this.identifier = identifier;
      this.productName = productName;
      this.customerIdentifier = customerIdentifier;
    }

    @Override
    public OrderData orderData() {
      return ImmutableOrderData
          .builder()
          .identifier(identifier)
          .productName(productName)
          .customerReferenceData(ImmutableCustomerReferenceData
              .builder()
              .customerIdentifier(customerIdentifier)
              .build())
          .build();
    }
  }
}
