package org.example.incoming.service.customer;

import org.example.dto.graph.CustomerData;
import org.example.framework.command.Command;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

@Immutable
public interface CustomerServiceCommand extends Command {

  @Nullable
  CustomerData customer();
}
