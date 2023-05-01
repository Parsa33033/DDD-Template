package org.example.test.mock.query.order;

import org.example.outgoing.repository.customer.CustomerQueryRepository;
import org.example.outgoing.repository.order.OrderQueryRepository;
import org.example.test.data.config.TestData;
import org.example.test.mock.config.MockConfig;
import org.example.test.mock.config.MockData;

public class OrderQueryRepositoryMockConfig implements
    MockConfig<TestData, OrderQueryRepository> {

  OrderQueryRepositoryMock customerQueryRepositoryMock;

  OrderQueryRepositoryMockSetup customerQueryRepositoryMockSetup;

  public OrderQueryRepositoryMockConfig() {
    customerQueryRepositoryMock = new OrderQueryRepositoryMock();
    customerQueryRepositoryMockSetup = new OrderQueryRepositoryMockSetup(
        customerQueryRepositoryMock);
  }

  @Override
  public OrderQueryRepository repository() {
    return customerQueryRepositoryMock.repository();
  }

  @Override
  public void setupMockData(final MockData<TestData> mockData) {
    customerQueryRepositoryMockSetup.setupMockData(mockData);
  }
}
