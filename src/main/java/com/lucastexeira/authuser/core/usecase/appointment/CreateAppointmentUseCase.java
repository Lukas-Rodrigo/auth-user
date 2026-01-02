package com.lucastexeira.authuser.core.usecase.appointment;

import com.lucastexeira.authuser.common.event.DomainEvent;
import com.lucastexeira.authuser.common.event.DomainEventPublisher;
import com.lucastexeira.authuser.core.domain.appointment.Appointment;
import com.lucastexeira.authuser.core.domain.businessservices.BusinessService;
import com.lucastexeira.authuser.core.exception.UserDoesNotOwnException;
import com.lucastexeira.authuser.core.port.in.CreateAppointmentInputPort;
import com.lucastexeira.authuser.core.port.out.appointment.SaveAppointmentOutputPort;
import com.lucastexeira.authuser.core.port.out.businessservices.FindBusinessServiceByIdOutputPort;
import com.lucastexeira.authuser.core.port.out.client.ExistsByIdAndUserIdOutputPort;
import com.lucastexeira.authuser.core.usecase.command.CreateAppointmentCommand;
import com.lucastexeira.authuser.core.usecase.command.ServiceItemCommand;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateAppointmentUseCase implements CreateAppointmentInputPort {

  private final ExistsByIdAndUserIdOutputPort existsByIdAndUserIdOutputPort;
  private final SaveAppointmentOutputPort saveAppointmentOutputPort;
  private final FindBusinessServiceByIdOutputPort findBusinessServiceByIdOutputPort;
  private final DomainEventPublisher domainEventPublisher;

  public CreateAppointmentUseCase(
      ExistsByIdAndUserIdOutputPort existsByIdAndUserIdOutputPort,
      SaveAppointmentOutputPort saveAppointmentOutputPort,
      FindBusinessServiceByIdOutputPort findBusinessServiceByIdOutputPort,
      DomainEventPublisher domainEventPublisher
  ) {
    this.existsByIdAndUserIdOutputPort = existsByIdAndUserIdOutputPort;
    this.domainEventPublisher = domainEventPublisher;
    this.saveAppointmentOutputPort = saveAppointmentOutputPort;
    this.findBusinessServiceByIdOutputPort = findBusinessServiceByIdOutputPort;
  }

  @Override
  public Appointment execute(CreateAppointmentCommand command) {
    UUID clientId = command.clientId();

    boolean ownsClient = this.existsByIdAndUserIdOutputPort.execute(
        clientId,
        command.userId()
    );

    if (!ownsClient) {
      throw new UserDoesNotOwnException("User does not own the client for this appointment");
    }

    if (command.scheduledAt().isBefore(LocalDateTime.now())) {
      throw new RuntimeException("Scheduled date must be in the future");
    }

    Appointment appointmentToCreate = new Appointment(
        command.scheduledAt(),
        clientId
    );

    for (ServiceItemCommand item : command.services()) {
      BusinessService service =
          findBusinessServiceByIdOutputPort.execute(item.businessServiceId());

      System.out.println("service: " + service.toString());


      if (service == null) {
        throw new RuntimeException(
            "Business service not found: " + item.businessServiceId());
      }

      if (!service.getUserId().equals(command.userId())) {
        System.out.println("Service user ID: " + service.getUserId());
        throw new UserDoesNotOwnException(
            "User does not own the business service: " + item.businessServiceId());
      }

      appointmentToCreate.addService(
          service,
          item.discount()
      );
    }
    Appointment.schedule(appointmentToCreate);

    var newAppointment =
        saveAppointmentOutputPort.execute(appointmentToCreate);

    for (DomainEvent event : appointmentToCreate.pullDomainEvents()) {
      domainEventPublisher.publish(event);
    }
    return newAppointment;
  }
}
