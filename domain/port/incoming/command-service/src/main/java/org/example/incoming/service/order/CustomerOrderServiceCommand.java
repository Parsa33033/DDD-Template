package org.example.incoming.service.order;

import org.example.dto.graph.CustomerData;
import org.example.dto.graph.OrderData;
import org.example.framework.command.Command;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

@Immutable
public interface CustomerOrderServiceCommand extends Command {

  @Nullable
  OrderData order();
}
