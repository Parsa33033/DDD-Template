package org.example.application.requests;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.springframework.http.MediaType;

public class CustomerRequest {

  private static final String ENDPOINT = "/customer";

  public static MockMvcResponse create(String requestBody) {
    return RestAssuredMockMvc
        .given()
        .body(requestBody)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .post(ENDPOINT);
  }

  public static MockMvcResponse update(String requestBody) {
    return RestAssuredMockMvc
        .given()
        .body(requestBody)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .post(ENDPOINT);
  }

  public static MockMvcResponse get(String identifier) {
    return RestAssuredMockMvc.when().get(ENDPOINT + "/" + identifier);
  }
}
