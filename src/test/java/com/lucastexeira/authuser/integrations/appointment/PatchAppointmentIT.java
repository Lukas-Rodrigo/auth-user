package com.lucastexeira.authuser.integrations.appointment;

import com.lucastexeira.authuser.adapters.out.persistence.entity.appointment.AppointmentEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.businesservice.BusinessServiceEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.client.ClientEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.user.UserEntity;
import com.lucastexeira.authuser.adapters.out.persistence.repository.AppointmentRepository;
import com.lucastexeira.authuser.adapters.out.persistence.repository.BusinessServiceRepository;
import com.lucastexeira.authuser.adapters.out.persistence.repository.ClientRepository;
import com.lucastexeira.authuser.adapters.out.persistence.repository.UserRepository;
import com.lucastexeira.authuser.integrations.factory.AppointmentTestFactory;
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

public class PatchAppointmentIT extends BaseIntegrationTest {


  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ClientRepository clientRepository;
  @Autowired
  private BusinessServiceRepository businessServiceRepository;

  @Autowired
  private AppointmentRepository appointmentRepository;
  @Autowired
  private PasswordEncoder encoder;


  private UserEntity testUser;
  private ClientEntity testClient;
  private BusinessServiceEntity testService;
  private AppointmentEntity testAppointment;


  @BeforeEach
  void setupTestData() {
//    // Clean database
//    appointmentRepository.deleteAll();
//    businessServiceRepository.deleteAll();
//    clientRepository.deleteAll();
//    userRepository.deleteAll();


    String email = "testuser" + UUID.randomUUID() + "@example.com";
    testUser = UserTestFactory.createUser(userRepository, encoder
        , email);
    testClient = ClientTestFactory.createClient(clientRepository, testUser);

    testService =
        BusinessServiceTestFactory.createBusinessServiceEntity(
            businessServiceRepository,
            testUser
        );

    testAppointment = AppointmentTestFactory.createAppointmentEntity(
        appointmentRepository,
        testUser,
        testService,
        testClient
        );


  }

  @Test
  void shouldReturn200_WhenPatchAppointmentWithValidaScheduleEvent() {
    String accessToken = this.loginAndGetAccessToken(testUser.getEmail(), "password123");

    String appointmentPayload = String.format(
        """
            {
                "schedule": "%s"
            }
            
            
            """
        ,
        LocalDateTime.now().plusDays(5).toString()
    );

    given()
        .contentType(ContentType.JSON)
        .cookie("access_token", accessToken)
        .body(appointmentPayload)
        .when()
        .patch("/api/appointments/schedule/{id}", testAppointment.getId())
        .then()
        .statusCode(200);

  }

  @Test
  void shouldReturn400_WhenPatchAppointmentWithPastScheduleEvent() {
    String accessToken = this.loginAndGetAccessToken(testUser.getEmail(), "password123");

    String appointmentPayload = String.format(
        """
            {
                "schedule": "%s"
            }
            
            
            """
        ,
        LocalDateTime.now().minusDays(5).toString()
    );

    given()
        .contentType(ContentType.JSON)
        .cookie("access_token", accessToken)
        .body(appointmentPayload)
        .when()
        .patch("/api/appointments/schedule/{id}", testAppointment.getId())
        .then()
        .statusCode(400);

  }

  @Test
  void shouldReturn200_WhenPatchAppointmentWithValidStatusData() {
    String accessToken = loginAndGetAccessToken(
        testUser.getEmail(),
        "password123"
    );

    String appointmentPayload = String.format("""
        {
            "status": "%s"
        }
        
        """,
    "CONFIRMED");

   given()
       .contentType(ContentType.JSON)
       .cookie("access_token", accessToken)
       .body(appointmentPayload)
       .when()
       .patch("/api/appointments/status/{id}", testAppointment.getId())
       .then()
       .statusCode(200);

  }

  @Test
  void shouldReturn400_WhenPatchAppointmentWithNoneExistentStatusData() {
    String accessToken = loginAndGetAccessToken(
        testUser.getEmail(),
        "password123"
    );

    String appointmentPayload = String.format("""
        {
            "status": "%s"
        }
        
        """,
        "invalid-status");

    given()
        .contentType(ContentType.JSON)
        .cookie("access_token", accessToken)
        .body(appointmentPayload)
        .when()
        .patch("/api/appointments/status/{id}", testAppointment.getId())
        .then()
        .statusCode(400);

  }
}
