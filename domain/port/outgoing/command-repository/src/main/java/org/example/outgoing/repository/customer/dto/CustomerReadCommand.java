package org.example.outgoing.repository.customer.dto;

import java.util.UUID;
import org.example.framework.command.ReadCommand;
import org.immutables.value.Value.Immutable;
import org.springframework.lang.Nullable;

@Immutable
public interface CustomerReadCommand extends ReadCommand {

  @Nullable
  UUID customerIdentifier();
}
