package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.time.Duration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.SocketUtils;

@SpringBootTest(webEnvironment = RANDOM_PORT,
    properties = "management.server.port=${test.port}")
public class RouteConfigurationTest {

  protected static int managementPort;
  @LocalServerPort
  protected int port = 0;

  protected WebTestClient webClient;

  protected String baseUri;

  @BeforeAll
  public static void beforeClass() {
    managementPort = SocketUtils.findAvailableTcpPort();

    System.setProperty("test.port", String.valueOf(managementPort));
  }

  @AfterAll
  public static void afterClass() {
    System.clearProperty("test.port");
  }

  @BeforeEach
  public void setup() {
    baseUri = "http://localhost:" + port;
    this.webClient = WebTestClient.bindToServer().responseTimeout(Duration.ofSeconds(10))
        .baseUrl(baseUri).build();
  }

  @Test
  public void contextLoads() {
    webClient.get().uri("/foo").exchange().expectStatus().isOk();
  }

  @Test
  public void responseFromFoo_ShouldHitBarFoo() {
    String response = webClient.get().uri("/foo").exchange().expectStatus()
        .isOk()
        .returnResult(new ParameterizedTypeReference<String>() {
        }).getResponseBody().blockLast();

    assertThat(response).isEqualTo("{\"name\":\"This is the one\"}");
    //Fails
    // expected: "{"name":"This is the one"}"
    // but was : "{"name":"Bob"}"

  }


}
