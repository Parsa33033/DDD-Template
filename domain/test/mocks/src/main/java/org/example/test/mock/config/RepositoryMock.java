package org.example.test.mock.config;

import java.util.UUID;
import org.example.framework.command.Command;
import org.example.framework.dto.DataTransferObject;
import org.example.framework.error.Error;
import org.example.framework.query.Query;
import org.example.framework.repository.QueryRepository;
import org.example.framework.repository.Repository;
import org.example.framework.result.Result;
import org.mockito.ArgumentMatcher;

public interface RepositoryMock<R extends Repository> {
  R repository();
}
