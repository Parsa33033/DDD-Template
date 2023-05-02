package org.example.application.builders;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;

public class CustomerDTOJsonBuilder {

  private static CustomerDTOJsonBuilder instance = new CustomerDTOJsonBuilder();
  private static JsonObject jsonObj = new JsonObject();

  private static String IDENTIFIER = "identifier";
  private static String NAME = "name";

  public JsonObject getJsonObject() {
    return jsonObj;
  }

  public static CustomerDTOJsonBuilder identifier(String identifier) {
    instance.getJsonObject().set(IDENTIFIER, identifier);
    return instance;
  }

  public static CustomerDTOJsonBuilder name(String name) {
    instance.getJsonObject().set(NAME, name);
    return instance;
  }

  public String buildJson() {
    return jsonObj.toString();
  }
}
