package org.example.framework.interaction;

import java.util.concurrent.CompletableFuture;
import org.example.framework.aggregate.AggregateCommandRepository;
import org.example.framework.aggregate.AggregateRoot;
import org.example.framework.error.Error;
import org.example.framework.result.Result;

public interface RepositoryInteraction<D, R, A extends AggregateRoot> {

  <T extends D> CompletableFuture<Result<T, Error>> read(T data);

  <T extends D> CompletableFuture<Result<R, Error>> write(T data);

  <T extends D> T updateAggregateRoot(T data, A aggregateRoot);
}
