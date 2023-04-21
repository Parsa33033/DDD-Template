package org.example.framework.error;

import org.example.framework.DomainConfig;

public final class ImmutableError implements Error {

  private final String code;
  private final String message;
  private final String hint;

  private ImmutableError(String code, String message, String hint) {
    this.code = code;
    this.message = message;
    this.hint = hint;
  }

  public static ImmutableError of(String code) {
    return new ImmutableError(code, tryGetMessageForCode(code), "");
  }

  @Override
  public String code() {
    return code;
  }

  public static ImmutableError of(String code, String hint) {
    return new ImmutableError(code, tryGetMessageForCode(code), hint);
  }

  public static String tryGetMessageForCode(String code) {
    return DomainConfig.instance().getLOV().get(code);
  }
}
