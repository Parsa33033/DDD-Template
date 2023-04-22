package org.example.test.mock.config;

import java.util.UUID;
import org.example.framework.command.Command;
import org.example.framework.dto.DataTransferObject;
import org.example.framework.error.Error;
import org.example.framework.repository.Repository;
import org.example.framework.result.Result;

public interface AggregateCommandRepositoryMock<R extends Repository, C extends Command,
    D extends DataTransferObject, E extends Error> extends
    RepositoryMock<R> {

  C captureWriteCommand(UUID identifier);

  void mockAnyWriteResult(Result<D, E> result);
}
