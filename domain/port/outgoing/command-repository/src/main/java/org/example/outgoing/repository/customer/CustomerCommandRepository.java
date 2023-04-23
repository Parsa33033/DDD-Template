package org.example.outgoing.repository.customer;

import java.util.concurrent.CompletableFuture;
import org.example.dto.aggregate.CustomerRegisterData;
import org.example.dto.graph.CustomerData;
import org.example.framework.aggregate.AggregateCommandRepository;
import org.example.framework.result.Nothing;
import org.example.framework.result.Result;
import org.example.outgoing.repository.customer.dto.CustomerReadCommand;
import org.example.outgoing.repository.customer.dto.CustomerWriteCommand;
import org.example.outgoing.repository.customer.error.CustomerReadError;
import org.example.outgoing.repository.customer.error.CustomerWriteError;

public interface CustomerCommandRepository extends
    AggregateCommandRepository<CustomerRegisterData, Nothing, CustomerReadError, CustomerWriteError,
        CustomerReadCommand, CustomerWriteCommand> {

  CompletableFuture<Result<CustomerRegisterData, CustomerReadError>> read(CustomerReadCommand command);

  CompletableFuture<Result<Nothing, CustomerWriteError>> write(CustomerWriteCommand command);
}
