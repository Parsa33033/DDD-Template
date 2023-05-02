package org.example.application;


import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import java.io.UnsupportedEncodingException;
import org.example.application.builders.CustomerDTOJsonBuilder;
import org.example.application.controllers.customer.CustomerApplication;
import org.example.application.requests.CustomerRequest;
import org.example.incoming.service.customer.CustomerCommandService;
import org.example.incoming.service.customer.CustomerQueryService;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
public class CustomerIntegrationTests {

  MockMvc mockMvc;

  @Autowired
  CustomerQueryService customerQueryService;

  @Autowired
  CustomerCommandService customerCommandService;

  @BeforeEach
  void setup() {
    mockMvc = MockMvcBuilders
        .standaloneSetup(new CustomerApplication(customerQueryService, customerCommandService))
        .build();
    RestAssuredMockMvc.mockMvc(mockMvc);
  }

  @Test
  public void successOnCreate() throws UnsupportedEncodingException, JSONException {

    MockMvcResponse createResponse = CustomerRequest.create(CustomerDTOJsonBuilder
        .name("parsa123").buildJson());
    createResponse.then().statusCode(200).body("identifier", Matchers.notNullValue());

    JSONObject jsonResultObject = new JSONObject(createResponse
        .getMvcResult()
        .getResponse().getContentAsString());

    CustomerRequest.get(jsonResultObject.getString("identifier")).then()
        .statusCode(200)
        .body("identifier", Matchers.equalTo(jsonResultObject.getString("identifier")));

  }

}
