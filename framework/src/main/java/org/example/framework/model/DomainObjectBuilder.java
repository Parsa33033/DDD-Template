package org.example.framework.model;

import java.util.function.Function;

public abstract class DomainObjectBuilder<O, D> {

  protected D dto;

  public DomainObjectBuilder(D dto) {
    this.dto = dto;
  }

  protected abstract O build();

  public static <U, V> U tryCreateObject(V value, Function<V, U> f) {
    if (value != null) {
      return f.apply(value);
    }
    return null;
  }

  public static <U, V> V tryGetObject(U u, Function<U, V> f) {
    if (u != null) {
      return f.apply(u);
    }
    return null;
  }
}
