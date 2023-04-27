package org.example.outgoing.repository.order.dto;

import org.example.dto.graph.CustomerData;
import org.example.dto.graph.OrderData;
import org.example.framework.command.WriteCommand;
import org.example.outgoing.dto.change.CustomerChange;
import org.example.outgoing.dto.change.OrderChange;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

@Immutable
public interface OrderWriteCommand extends WriteCommand {

  @Nullable
  OrderData order();

  @Nullable
  OrderChange orderChange();
}
