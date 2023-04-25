//package org.example.service.command;
//
//import static org.junit.Assert.assertTrue;
//
//import java.util.HashMap;
//import java.util.concurrent.ExecutionException;
//import org.example.dto.graph.CustomerData;
//import org.example.framework.result.Result;
//import org.example.incoming.service.customer.CustomerServiceError;
//import org.springframework.test.context.TestContext;
//
//public class CustomerCommandServiceInteractionStepDefs {
//
//  TestContext testContext;
//
//  private final CustomerRepositoryMockConfig customerQueryRepositoryMockConfig;
//
//  private final CustomerQueryService customerQueryService;
//
//  private Result<CustomerData, CustomerServiceError> result;
//
//  public CustomerCommandServiceInteractionStepDefs() {
//    testContext = new TestContext();
//    customerQueryRepositoryMockConfig = new CustomerQueryRepositoryMockConfig();
//    customerQueryService =
//        new CustomerQueryInteractionService(customerQueryRepositoryMockConfig.repository());
//  }
//
//  @Given("customer {customerTest}")
//  public void givenCustomer(CustomerTest customerTest) {
//    testContext.testData().addCustomer(customerTest.customerData());
//  }
//
//  @When("customer {customerTest} is queried")
//  public void customerIsQueried(CustomerTest customerTest)
//      throws ExecutionException, InterruptedException {
//    setupMockData();
//
//    this.result = customerQueryService.getCustomerByUUID(ImmutableCustomerServiceQuery
//        .builder().customerIdentifier(customerTest.customerData()
//        .identifier()).build()).get();
//
//    this.testContext.result((Result) result);
//  }
//
//  @Then("customer {customerTest} is returned")
//  public void customerIsReturned(CustomerTest customerTest) {
//    assertTrue(result.isOk());
//    assertTrue(result.object.identifier().equals(customerTest.customerData().identifier()));
//  }
//
//  private void setupMockData() {
//    MockData<TestData> mockData = ImmutableApplicationMockData
//        .builder()
//        .data(testContext.testData())
//        .failureMap(new HashMap<>())
//        .build();
//    customerQueryRepositoryMockConfig.setupMockData(mockData);
//  }
//}
