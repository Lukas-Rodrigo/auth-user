package com.lucastexeira.authuser.integrations.businessservice;

import com.lucastexeira.authuser.adapters.out.persistence.entity.user.UserEntity;
import com.lucastexeira.authuser.adapters.out.persistence.repository.UserRepository;
import com.lucastexeira.authuser.integrations.factory.UserTestFactory;
import com.lucastexeira.authuser.integrations.setup.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateBusinessServiceIT extends BaseIntegrationTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private UserEntity testUser;

  @BeforeEach
  void setupTestData() {
    String email = "testuser" + UUID.randomUUID() + "@example.com";
    testUser = UserTestFactory.createUser(userRepository, passwordEncoder, email);
  }

  @Test
  void shouldCreateBusinessService_WhenDataIsValid() {
    String accessToken = loginAndGetAccessToken(testUser.getEmail(), "password123");

    String businessServicePayload = """
        {
          "name": "Test Service",
          "price": 150,
          "duration": 60
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", accessToken)
        .body(businessServicePayload)
        .when()
        .post("/api/business-services")
        .then()
        .statusCode(201)
        .body("id", notNullValue())
        .body("name", equalTo("Test Service"))
        .body("price", equalTo(150))
        .body("duration", equalTo(60));
  }

  @Test
  void shouldReturn400_WhenNameIsNull() {
    String accessToken = loginAndGetAccessToken(testUser.getEmail(), "password123");

    String businessServicePayload = """
        {
          "price": 150,
          "duration": 60
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", accessToken)
        .body(businessServicePayload)
        .when()
        .post("/api/business-services")
        .then()
        .statusCode(400);
  }

  @Test
  void shouldReturn400_WhenNameIsBlank() {
    String accessToken = loginAndGetAccessToken(testUser.getEmail(), "password123");

    String businessServicePayload = """
        {
          "name": "",
          "price": 150,
          "duration": 60
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", accessToken)
        .body(businessServicePayload)
        .when()
        .post("/api/business-services")
        .then()
        .statusCode(400);
  }

  @Test
  void shouldReturn400_WhenPriceIsNull() {
    String accessToken = loginAndGetAccessToken(testUser.getEmail(), "password123");

    String businessServicePayload = """
        {
          "name": "Test Service",
          "duration": 60
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", accessToken)
        .body(businessServicePayload)
        .when()
        .post("/api/business-services")
        .then()
        .statusCode(400);
  }

  @Test
  void shouldReturn400_WhenPriceIsNegative() {
    String accessToken = loginAndGetAccessToken(testUser.getEmail(), "password123");

    String businessServicePayload = """
        {
          "name": "Test Service",
          "price": -10,
          "duration": 60
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", accessToken)
        .body(businessServicePayload)
        .when()
        .post("/api/business-services")
        .then()
        .statusCode(400);
  }

  @Test
  void shouldReturn400_WhenDurationIsNull() {
    String accessToken = loginAndGetAccessToken(testUser.getEmail(), "password123");

    String businessServicePayload = """
        {
          "name": "Test Service",
          "price": 150
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", accessToken)
        .body(businessServicePayload)
        .when()
        .post("/api/business-services")
        .then()
        .statusCode(400);
  }

  @Test
  void shouldReturn400_WhenDurationIsNegative() {
    String accessToken = loginAndGetAccessToken(testUser.getEmail(), "password123");

    String businessServicePayload = """
        {
          "name": "Test Service",
          "price": 150,
          "duration": -30
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", accessToken)
        .body(businessServicePayload)
        .when()
        .post("/api/business-services")
        .then()
        .statusCode(400);
  }

  @Test
  void shouldReturn400_WhenPriceIsZero() {
    String accessToken = loginAndGetAccessToken(testUser.getEmail(), "password123");

    String businessServicePayload = """
        {
          "name": "Test Service",
          "price": 0,
          "duration": 60
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", accessToken)
        .body(businessServicePayload)
        .when()
        .post("/api/business-services")
        .then()
        .statusCode(400);
  }

  @Test
  void shouldReturn400_WhenDurationIsZero() {
    String accessToken = loginAndGetAccessToken(testUser.getEmail(), "password123");

    String businessServicePayload = """
        {
          "name": "Test Service",
          "price": 150,
          "duration": 0
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", accessToken)
        .body(businessServicePayload)
        .when()
        .post("/api/business-services")
        .then()
        .statusCode(400);
  }

  @Test
  void shouldReturn403_WhenUserIsNotAuthenticated() {
    String businessServicePayload = """
        {
          "name": "Test Service",
          "price": 150,
          "duration": 60
        }
        """;

    given()
        .contentType("application/json")
        .body(businessServicePayload)
        .when()
        .post("/api/business-services")
        .then()
        .statusCode(403);
  }

  @Test
  void shouldReturn403_WhenAccessTokenIsInvalid() {
    String businessServicePayload = """
        {
          "name": "Test Service",
          "price": 150,
          "duration": 60
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", "invalid-token")
        .body(businessServicePayload)
        .when()
        .post("/api/business-services")
        .then()
        .statusCode(403);
  }
}
