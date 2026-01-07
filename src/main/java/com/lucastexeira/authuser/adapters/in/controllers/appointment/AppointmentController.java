package com.lucastexeira.authuser.adapters.in.controllers.appointment;

import com.lucastexeira.authuser.adapters.in.controllers.appointment.dto.request.AppointmentPatchScheduleRequest;
import com.lucastexeira.authuser.adapters.in.controllers.appointment.dto.request.AppointmentPatchStatusRequest;
import com.lucastexeira.authuser.adapters.in.controllers.appointment.dto.request.AppointmentRequest;
import com.lucastexeira.authuser.adapters.in.controllers.appointment.mapper.AppointmentMapper;
import com.lucastexeira.authuser.common.dto.PaginationResponse;
import com.lucastexeira.authuser.adapters.out.persistence.repository.AppointmentRepository;
import com.lucastexeira.authuser.common.enums.AppointmentStatus;
import com.lucastexeira.authuser.core.port.in.appointment.CreateAppointmentInputPort;
import com.lucastexeira.authuser.core.port.in.appointment.FetchAppointmentInputPort;
import com.lucastexeira.authuser.core.port.in.appointment.PatchAppointmentScheduleInputPort;
import com.lucastexeira.authuser.core.port.in.appointment.PatchAppointmentStatusInputPort;
import com.lucastexeira.authuser.core.port.in.businessservice.FetchBusinessServicesInputPort;
import com.lucastexeira.authuser.core.usecase.command.*;
import com.lucastexeira.authuser.core.usecase.query.FetchQuery;
import com.lucastexeira.authuser.core.usecase.result.AppointmentDetailsResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/appointments")
public class AppointmentController {

  private final Logger logger = Logger.getLogger(AppointmentController.class.getName());

  private final CreateAppointmentInputPort createAppointmentInputPort;

  private final PatchAppointmentScheduleInputPort patchAppointmentScheduleInputPort;

  private final PatchAppointmentStatusInputPort patchAppointmentStatusInputPort;

  private final FetchAppointmentInputPort fetchAppointmentInputPort;


  private final FetchBusinessServicesInputPort fetchBusinessServicesInputPort;

  @Autowired
  private AppointmentRepository appointmentRepository;

  public AppointmentController(
      CreateAppointmentInputPort createAppointmentInputPort,
      PatchAppointmentScheduleInputPort patchAppointmentScheduleInputPort,
      PatchAppointmentStatusInputPort patchAppointmentStatusInputPort,
      FetchAppointmentInputPort fetchAppointmentInputPort,
      FetchBusinessServicesInputPort fetchBusinessServicesInputPort
  ) {
    this.patchAppointmentStatusInputPort = patchAppointmentStatusInputPort;
    this.patchAppointmentScheduleInputPort = patchAppointmentScheduleInputPort;
    this.createAppointmentInputPort = createAppointmentInputPort;
    this.fetchAppointmentInputPort = fetchAppointmentInputPort;
    this.fetchBusinessServicesInputPort = fetchBusinessServicesInputPort;
  }

  @PostMapping()
  public ResponseEntity<Void> create(
      Authentication authentication,
      @RequestBody() AppointmentRequest request
  ) {
    UUID userId = UUID.fromString(authentication.getName());

    CreateAppointmentCommand command = new CreateAppointmentCommand(
        userId,
        request.clientId(),
        request.scheduledAt(),
        request.services().stream().map(s -> new ServiceItemCommand(
            s.businessServiceId(),
            s.discount()
        )).toList());

    createAppointmentInputPort.execute(command);

    return ResponseEntity.ok().build();
  }

  @PatchMapping("/status/{id}")
  public ResponseEntity<Void> patchStatus(
      Authentication authentication,
      @RequestBody() @Valid AppointmentPatchStatusRequest request,
      @PathVariable UUID id
  ) {
    UUID userId = UUID.fromString(authentication.getName());
    logger.info("User " + userId + " is updating appointment status.");
    var command = new PatchAppointmentCommand(
        userId,
        id,
        AppointmentStatus.valueOf(request.status())
    );
    patchAppointmentStatusInputPort.execute(command);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/schedule/{id}")
  public ResponseEntity<Void> patchSchedule(
      Authentication authentication,
      @RequestBody() @Valid AppointmentPatchScheduleRequest request
      , @PathVariable UUID id
  ) {
    UUID userId = UUID.fromString(authentication.getName());

    var command = new PatchAppointmentScheduleCommand(
        userId,
        id,
        request.schedule()
    );

    this.patchAppointmentScheduleInputPort.execute(command);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<PaginationResponse<AppointmentDetailsResult>> fetchAll(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "15") int size,
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam(required = false) @DateTimeFormat(iso =
          DateTimeFormat.ISO.DATE) LocalDate endDate,
      Authentication authentication
  ) {
    UUID userId = UUID.fromString(authentication.getName());
    var fetchQuery = new FetchQuery(page, size, startDate, endDate);
    var command = new FetchAppointmentCommand(
        userId,
        fetchQuery
    );

    var result = this.fetchAppointmentInputPort.execute(command);
    return ResponseEntity.ok(AppointmentMapper.toResponse(result));
  }

}
