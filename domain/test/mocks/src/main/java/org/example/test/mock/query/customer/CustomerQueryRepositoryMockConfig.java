package org.example.test.mock.query.customer;

import org.example.outgoing.repository.customer.CustomerQueryRepository;
import org.example.test.data.config.TestData;
import org.example.test.mock.config.MockConfig;
import org.example.test.mock.config.MockData;

public class CustomerQueryRepositoryMockConfig implements
    MockConfig<TestData, CustomerQueryRepository> {

  CustomerQueryRepositoryMock customerQueryRepositoryMock;

  CustomerQueryRepositoryMockSetup customerQueryRepositoryMockSetup;

  public CustomerQueryRepositoryMockConfig() {
    customerQueryRepositoryMock = new CustomerQueryRepositoryMock();
    customerQueryRepositoryMockSetup = new CustomerQueryRepositoryMockSetup(
        customerQueryRepositoryMock);
  }

  @Override
  public CustomerQueryRepository repository() {
    return customerQueryRepositoryMock.repository();
  }

  @Override
  public void setupMockData(final MockData<TestData> mockData) {
    customerQueryRepositoryMockSetup.setupMockData(mockData);
  }
}
