package com.lucastexeira.authuser.core.usecase.appointment;

import com.lucastexeira.authuser.common.enums.AppointmentStatus;
import com.lucastexeira.authuser.common.event.DomainEvent;
import com.lucastexeira.authuser.common.event.DomainEventPublisher;
import com.lucastexeira.authuser.core.domain.appointment.Appointment;
import com.lucastexeira.authuser.core.exception.UserDoesNotOwnException;
import com.lucastexeira.authuser.core.port.in.PatchAppointmentStatusInputPort;
import com.lucastexeira.authuser.core.port.out.appointment.FindAppointmentByIdOutputPort;
import com.lucastexeira.authuser.core.port.out.appointment.SaveAppointmentOutputPort;
import com.lucastexeira.authuser.core.port.out.client.ExistsByIdAndUserIdOutputPort;
import com.lucastexeira.authuser.core.usecase.command.PatchAppointmentCommand;

public class PatchAppointmentStatusInputPortUseCase implements
    PatchAppointmentStatusInputPort {

  private final ExistsByIdAndUserIdOutputPort existsByIdAndUserIdOutputPort;

  private final FindAppointmentByIdOutputPort findAppointmentByIdOutputPort;


  private final SaveAppointmentOutputPort saveAppointmentOutputPort;

  private final DomainEventPublisher domainEventPublisher;

  public PatchAppointmentStatusInputPortUseCase(
      ExistsByIdAndUserIdOutputPort existsByIdAndUserIdOutputPort,
      FindAppointmentByIdOutputPort findAppointmentByIdOutputPort,
      SaveAppointmentOutputPort saveAppointmentOutputPort,
      DomainEventPublisher domainEventPublisher
  ) {
    this.existsByIdAndUserIdOutputPort = existsByIdAndUserIdOutputPort;
    this.domainEventPublisher = domainEventPublisher;
    this.findAppointmentByIdOutputPort = findAppointmentByIdOutputPort;
    this.saveAppointmentOutputPort = saveAppointmentOutputPort;
  }

  @Override
  public Appointment execute(PatchAppointmentCommand command) {
    Appointment appointment =
        this.findAppointmentByIdOutputPort.execute(command.appointmentId());

    if (appointment == null) {
      throw new RuntimeException("Appointment not found");
    }

    boolean ownsClient = this.existsByIdAndUserIdOutputPort.execute(
        appointment.getClientId(),
        command.userId()
    );

    if (!ownsClient) {
      throw new UserDoesNotOwnException("User does not own the client for this appointment");
    }


    if (command.status() == null) {
      return appointment;
    }


    AppointmentStatus status = AppointmentStatus.fromString(command.status().getStatus());
    appointment.updateStatus(status);
    Appointment savedAppointment =
        this.saveAppointmentOutputPort.execute(appointment);

    for (DomainEvent event : savedAppointment.pullDomainEvents()) {
      this.domainEventPublisher.publish(event);
    }
    return savedAppointment;
  }
}
