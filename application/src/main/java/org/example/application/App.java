package org.example.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"org.example", "org.example.infra"})
@EntityScan("org.example.infra.postgres.model")
@EnableJpaRepositories("org.example.infra.postgres.repository")
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
