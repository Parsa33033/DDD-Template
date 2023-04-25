package org.example.outgoing.repository.customer.dto;

import org.example.dto.graph.CustomerData;
import org.example.framework.command.WriteCommand;
import org.example.outgoing.dto.change.CustomerChange;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

@Immutable
public interface CustomerWriteCommand extends WriteCommand {

  @Nullable
  CustomerData customer();

  @Nullable
  CustomerChange customerChange();
}
