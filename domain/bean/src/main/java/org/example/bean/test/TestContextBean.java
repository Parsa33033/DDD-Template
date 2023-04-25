package org.example.bean.test;

import org.example.test.data.config.TestContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TestContextBean {

  @Bean
  public TestContext testContext() {
    return new TestContext();
  }
}
