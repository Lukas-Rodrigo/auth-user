package com.lucastexeira.authuser.integrations.setup;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BaseIntegrationTest {

  @LocalServerPort
  protected int port;

  @BeforeEach()
  void setup() {
    RestAssured.port = port;

    RestAssured.baseURI = "http://localhost";
  }

  protected String loginAndGetAccessToken(String email, String password) {
    Response response = given()
        .contentType(ContentType.JSON)
        .body(String.format("""
            {
              "email": "%s",
              "password": "%s"
            }
            """, email, password))
        .when()
        .post("/api/auth/login");

    return response.getCookie("access_token");
  }
}
