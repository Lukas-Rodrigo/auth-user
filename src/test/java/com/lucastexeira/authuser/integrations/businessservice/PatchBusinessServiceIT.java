package com.lucastexeira.authuser.integrations.businessservice;

import com.lucastexeira.authuser.adapters.out.persistence.entity.businesservice.BusinessServiceEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.user.UserEntity;
import com.lucastexeira.authuser.adapters.out.persistence.repository.BusinessServiceRepository;
import com.lucastexeira.authuser.adapters.out.persistence.repository.UserRepository;
import com.lucastexeira.authuser.integrations.factory.BusinessServiceTestFactory;
import com.lucastexeira.authuser.integrations.factory.UserTestFactory;
import com.lucastexeira.authuser.integrations.setup.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PatchBusinessServiceIT extends BaseIntegrationTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BusinessServiceRepository businessServiceRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private UserEntity testUser;
  private BusinessServiceEntity testService;

  @BeforeEach
  void setupTestData() {
    String email = "testuser" + UUID.randomUUID() + "@example.com";
    testUser = UserTestFactory.createUser(userRepository, passwordEncoder,
        email);
    testService = BusinessServiceTestFactory.createBusinessServiceEntity(
        businessServiceRepository, testUser);
  }

  @Test
  void shouldUpdateBusinessService_WhenAllFieldsAreValid() {
    String accessToken = loginAndGetAccessToken(testUser.getEmail(),
        "password123");

    String patchPayload = """
        {
          "name": "Updated Service",
          "price": 200,
          "duration": 90
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", accessToken)
        .body(patchPayload)
        .when()
        .patch("/api/business-services/" + testService.getId())
        .then()
        .statusCode(200)
        .body("id", equalTo(testService.getId().toString()))
        .body("name", equalTo("Updated Service"))
        .body("price", equalTo(200))
        .body("duration", equalTo(90));
  }

  @Test
  void shouldUpdateBusinessService_WhenOnlyNameIsProvided() {
    String accessToken = loginAndGetAccessToken(testUser.getEmail(),
        "password123");

    String patchPayload = """
        {
          "name": "Only Name Updated"
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", accessToken)
        .body(patchPayload)
        .when()
        .patch("/api/business-services/" + testService.getId())
        .then()
        .statusCode(200)
        .body("id", equalTo(testService.getId().toString()))
        .body("name", equalTo("Only Name Updated"))
        .body("price", equalTo(testService.getPrice()))
        .body("duration", equalTo(testService.getDuration()));
  }

  @Test
  void shouldUpdateBusinessService_WhenOnlyPriceIsProvided() {
    String accessToken = loginAndGetAccessToken(testUser.getEmail(),
        "password123");

    String patchPayload = """
        {
          "price": 300
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", accessToken)
        .body(patchPayload)
        .when()
        .patch("/api/business-services/" + testService.getId())
        .then()
        .statusCode(200)
        .body("id", equalTo(testService.getId().toString()))
        .body("name", equalTo(testService.getName()))
        .body("price", equalTo(300))
        .body("duration", equalTo(testService.getDuration()));
  }

  @Test
  void shouldUpdateBusinessService_WhenOnlyDurationIsProvided() {
    String accessToken = loginAndGetAccessToken(testUser.getEmail(),
        "password123");

    String patchPayload = """
        {
          "duration": 120
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", accessToken)
        .body(patchPayload)
        .when()
        .patch("/api/business-services/" + testService.getId())
        .then()
        .statusCode(200)
        .body("id", equalTo(testService.getId().toString()))
        .body("name", equalTo(testService.getName()))
        .body("price", equalTo(testService.getPrice()))
        .body("duration", equalTo(120));
  }

  @Test
  void shouldReturn400_WhenNameIsTooShort() {
    String accessToken = loginAndGetAccessToken(testUser.getEmail(),
        "password123");

    String patchPayload = """
        {
          "name": "A"
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", accessToken)
        .body(patchPayload)
        .when()
        .patch("/api/business-services/" + testService.getId())
        .then()
        .statusCode(400);
  }

  @Test
  void shouldReturn400_WhenPriceIsZero() {
    String accessToken = loginAndGetAccessToken(testUser.getEmail(),
        "password123");

    String patchPayload = """
        {
          "price": 0
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", accessToken)
        .body(patchPayload)
        .when()
        .patch("/api/business-services/" + testService.getId())
        .then()
        .statusCode(400);
  }

  @Test
  void shouldReturn400_WhenPriceIsNegative() {
    String accessToken = loginAndGetAccessToken(testUser.getEmail(),
        "password123");

    String patchPayload = """
        {
          "price": -50
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", accessToken)
        .body(patchPayload)
        .when()
        .patch("/api/business-services/" + testService.getId())
        .then()
        .statusCode(400);
  }

  @Test
  void shouldReturn400_WhenDurationIsZero() {
    String accessToken = loginAndGetAccessToken(testUser.getEmail(),
        "password123");

    String patchPayload = """
        {
          "duration": 0
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", accessToken)
        .body(patchPayload)
        .when()
        .patch("/api/business-services/" + testService.getId())
        .then()
        .statusCode(400);
  }

  @Test
  void shouldReturn400_WhenDurationIsNegative() {
    String accessToken = loginAndGetAccessToken(testUser.getEmail(),
        "password123");

    String patchPayload = """
        {
          "duration": -30
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", accessToken)
        .body(patchPayload)
        .when()
        .patch("/api/business-services/" + testService.getId())
        .then()
        .statusCode(400);
  }

  @Test
  void shouldReturn404_WhenBusinessServiceDoesNotExist() {
    String accessToken = loginAndGetAccessToken(testUser.getEmail(),
        "password123");

    String patchPayload = """
        {
          "name": "Updated Service"
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", accessToken)
        .body(patchPayload)
        .when()
        .patch("/api/business-services/" + UUID.randomUUID())
        .then()
        .statusCode(404);
  }

  @Test
  void shouldReturn403_WhenUserIsNotAuthenticated() {
    String patchPayload = """
        {
          "name": "Updated Service"
        }
        """;

    given()
        .contentType("application/json")
        .body(patchPayload)
        .when()
        .patch("/api/business-services/" + testService.getId())
        .then()
        .statusCode(403);
  }

  @Test
  void shouldReturn403_WhenAccessTokenIsInvalid() {
    String patchPayload = """
        {
          "name": "Updated Service"
        }
        """;

    given()
        .contentType("application/json")
        .cookie("access_token", "invalid-token")
        .body(patchPayload)
        .when()
        .patch("/api/business-services/" + testService.getId())
        .then()
        .statusCode(403);
  }
}
