package org.example.application.builders;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;

public final class OrderDTOJsonBuilder {

  private static OrderDTOJsonBuilder instance = new OrderDTOJsonBuilder();
  private static JsonObject jsonObj = new JsonObject();

  private static String IDENTIFIER = "identifier";
  private static String PRODUCT_NAME = "productName";
  private static String CUSTOMER_IDENTIFIER = "customerIdentifier";

  public JsonObject getJsonObject() {
    return jsonObj;
  }

  public static OrderDTOJsonBuilder identifier(String identifier) {
    instance.getJsonObject().set(IDENTIFIER, identifier);
    return instance;
  }

  public static OrderDTOJsonBuilder productName(String name) {
    instance.getJsonObject().set(PRODUCT_NAME, name);
    return instance;
  }

  public static OrderDTOJsonBuilder customerIdentifier(String customerIdentifier) {
    instance.getJsonObject().set(CUSTOMER_IDENTIFIER, customerIdentifier);
    return instance;
  }

  public String buildJson() {
    return jsonObj.toString();
  }
}