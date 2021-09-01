package com.example.atm_machine.integration;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "local")
@AutoConfigureWebTestClient
@DirtiesContext
public class ApplicationContextIT {

  @ClassRule
  public static final MySqlIntegrationClassRule mySqlIntegrationClassRule = new MySqlIntegrationClassRule();

  @Autowired
  private WebTestClient webTestClient;

  @Test
  public void applicationIsHealthyAfterStart() {
    webTestClient.get().uri("/health").exchange().expectStatus().isOk();
  }

}