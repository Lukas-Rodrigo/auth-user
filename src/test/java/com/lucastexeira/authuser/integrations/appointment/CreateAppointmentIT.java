package com.lucastexeira.authuser.integrations.appointment;

import com.lucastexeira.authuser.adapters.out.persistence.entity.businesservice.BusinessServiceEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.client.ClientEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.user.UserEntity;
import com.lucastexeira.authuser.adapters.out.persistence.repository.BusinessServiceRepository;
import com.lucastexeira.authuser.adapters.out.persistence.repository.ClientRepository;
import com.lucastexeira.authuser.adapters.out.persistence.repository.UserRepository;
import com.lucastexeira.authuser.integrations.factory.BusinessServiceTestFactory;
import com.lucastexeira.authuser.integrations.factory.ClientTestFactory;
import com.lucastexeira.authuser.integrations.factory.UserTestFactory;
import com.lucastexeira.authuser.integrations.setup.BaseIntegrationTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class CreateAppointmentIT extends BaseIntegrationTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private BusinessServiceRepository businessServiceRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;


  private UserEntity testUser;
  private ClientEntity testClient;
  private BusinessServiceEntity testService;

  @BeforeEach
  void setupTestData() {
    String email = "testuser" + UUID.randomUUID() + "@example.com";
    testUser = UserTestFactory.createUser(userRepository, passwordEncoder
        , email);
     testClient = ClientTestFactory.createClient(clientRepository, testUser);

     testService =
         BusinessServiceTestFactory.createBusinessServiceEntity(
            businessServiceRepository,
            testUser
        );
  }

  @Test
  void shouldCreateAppointment_WhenUserOwnsClientAndService() {
    // Login and get access token
    String accessToken = loginAndGetAccessToken(testUser.getEmail(),
        "password123");

    // Create appointment payload with real IDs
    String appointmentPayload = String.format("""
            {
              "clientId": "%s",
              "scheduledAt": "%s",
              "services": [
                {
                  "businessServiceId": "%s",
                  "discount": 0
                }
              ]
            }
            """,
        testClient.getId(),
        LocalDateTime.now().plusDays(1),
        testService.getId()
    );

    given()
        .contentType(ContentType.JSON)
        .cookie("access_token", accessToken)
        .body(appointmentPayload)
        .when()
        .post("/api/appointments")
        .then()
        .statusCode(200);
  }

  @Test
  void shouldReturn403_WhenUserTriesCreateAppointmentForAnotherUsersClient() {
    UserTestFactory.createUser(userRepository,
        passwordEncoder
        , "seconduser@gmail.com");


    String accessToken = loginAndGetAccessToken("seconduser@gmail.com",
        "password123");

    String appointmentPayload = String.format("""
            {
              "clientId": "%s",
              "scheduledAt": "%s",
              "services": [
                {
                  "businessServiceId": "%s",
                  "discount": 0
                }
              ]
            }
            """,
        testClient.getId(),
        LocalDateTime.now().plusDays(1),
        testService.getId()
    );

    given()
        .contentType(ContentType.JSON)
        .cookie("access_token", accessToken)
        .body(appointmentPayload)
        .when()
        .post("/api/appointments")
        .then()
        .statusCode(403);

  }

  @Test
  void shouldReturn403_WhenUserTriesCreateAppointmentWithAnotherBusinessUsersService() {
    var invalidUser = UserTestFactory.createUser(userRepository,
        passwordEncoder
        , "secondUser@example.com");

    var invalidBusinessService =
        BusinessServiceTestFactory.createBusinessServiceEntity(
            businessServiceRepository
            , invalidUser);


    String accessToken = loginAndGetAccessToken("secondUser@example.com",
        "password123");

    String appointmentPayload = String.format("""
            {
              "clientId": "%s",
              "scheduledAt": "%s",
              "services": [
                {
                  "businessServiceId": "%s",
                  "discount": 0
                },
                {
                  "businessServiceId": "%s",
                  "discount": 0
                }
              ]
            }
            """,
        testClient.getId(),
        LocalDateTime.now().plusDays(1),
        invalidBusinessService.getId(),
        testService.getId()
    );

    given()
        .contentType(ContentType.JSON)
        .cookie("access_token", accessToken)
        .body(appointmentPayload)
        .when()
        .post("/api/appointments")
        .then()
        .statusCode(403);
  }
}
