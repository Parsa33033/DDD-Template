package org.example.test.mock.config;

import java.util.Map;
import org.example.test.mock.config.failure.TestFailure;

public interface MockData<D> {

  D data();

  Map<RepositoryOperation, TestFailure> failureMap();
}
