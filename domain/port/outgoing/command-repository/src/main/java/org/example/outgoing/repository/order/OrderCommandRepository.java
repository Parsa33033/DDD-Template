package org.example.outgoing.repository.order;

import java.util.concurrent.CompletableFuture;
import org.example.dto.aggregate.CustomerOrderData;
import org.example.dto.aggregate.CustomerRegisterData;
import org.example.framework.aggregate.AggregateCommandRepository;
import org.example.framework.result.Nothing;
import org.example.framework.result.Result;
import org.example.outgoing.repository.customer.dto.CustomerReadCommand;
import org.example.outgoing.repository.customer.dto.CustomerWriteCommand;
import org.example.outgoing.repository.customer.error.CustomerReadError;
import org.example.outgoing.repository.customer.error.CustomerWriteError;
import org.example.outgoing.repository.order.dto.OrderReadCommand;
import org.example.outgoing.repository.order.dto.OrderWriteCommand;
import org.example.outgoing.repository.order.error.OrderReadError;
import org.example.outgoing.repository.order.error.OrderWriteError;

public interface OrderCommandRepository extends
    AggregateCommandRepository<CustomerOrderData, Nothing, OrderReadError, OrderWriteError,
        OrderReadCommand, OrderWriteCommand> {

}
