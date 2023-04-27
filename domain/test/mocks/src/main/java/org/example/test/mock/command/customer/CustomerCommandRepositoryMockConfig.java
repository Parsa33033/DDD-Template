package org.example.test.mock.command.customer;

import java.util.List;
import java.util.UUID;
import org.example.outgoing.repository.customer.CustomerCommandRepository;
import org.example.outgoing.repository.customer.CustomerQueryRepository;
import org.example.outgoing.repository.customer.dto.CustomerWriteCommand;
import org.example.test.data.config.TestData;
import org.example.test.mock.config.CaptorResult;
import org.example.test.mock.config.MockConfig;
import org.example.test.mock.config.MockData;
import org.example.test.mock.query.customer.CustomerQueryRepositoryMockSetup;

public class CustomerCommandRepositoryMockConfig implements
    MockConfig<TestData, CustomerCommandRepository>, CaptorResult<CustomerWriteCommand> {

  CustomerCommandRepositoryMock customerCommandRepositoryMock;

  CustomerCommandRepositoryMockSetup customerCommandRepositoryMockSetup;

  public CustomerCommandRepositoryMockConfig() {
    customerCommandRepositoryMock = new CustomerCommandRepositoryMock();
    customerCommandRepositoryMockSetup = new CustomerCommandRepositoryMockSetup(
        customerCommandRepositoryMock);
  }

  @Override
  public CustomerCommandRepository repository() {
    return customerCommandRepositoryMock.repository();
  }

  @Override
  public CustomerWriteCommand captureCommand(UUID identifier) {
    return customerCommandRepositoryMock.captureWriteCommand(identifier);
  }

  @Override
  public List<CustomerWriteCommand> captureAllCommands() {
    return customerCommandRepositoryMock.captureAllWriteCommands();
  }

  @Override
  public void setupMockData(final MockData<TestData> mockData) {
    customerCommandRepositoryMockSetup.setupMockData(mockData);
  }
}
