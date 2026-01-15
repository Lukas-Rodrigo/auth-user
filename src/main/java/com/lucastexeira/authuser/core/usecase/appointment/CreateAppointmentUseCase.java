package com.lucastexeira.authuser.core.usecase.appointment;

import com.lucastexeira.authuser.common.event.DomainEvent;
import com.lucastexeira.authuser.common.event.DomainEventPublisher;
import com.lucastexeira.authuser.core.domain.appointment.Appointment;
import com.lucastexeira.authuser.core.domain.businessservices.BusinessService;
import com.lucastexeira.authuser.core.domain.valueobject.Money;
import com.lucastexeira.authuser.core.exception.BusinessServiceNotFoundException;
import com.lucastexeira.authuser.core.exception.InvalidScheduleDateException;
import com.lucastexeira.authuser.core.exception.UserDoesNotOwnException;
import com.lucastexeira.authuser.core.port.in.appointment.CreateAppointmentInputPort;
import com.lucastexeira.authuser.core.port.out.appointment.FindActiveAppointmentByUserAndDayOutputPort;
import com.lucastexeira.authuser.core.port.out.appointment.SaveAppointmentOutputPort;
import com.lucastexeira.authuser.core.port.out.businessservices.FindBusinessServiceByIdOutputPort;
import com.lucastexeira.authuser.core.port.out.client.ExistsByIdAndUserIdOutputPort;
import com.lucastexeira.authuser.core.service.AppointmentScheduleService;
import com.lucastexeira.authuser.core.usecase.command.CreateAppointmentCommand;
import com.lucastexeira.authuser.core.usecase.command.ServiceItemCommand;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CreateAppointmentUseCase implements CreateAppointmentInputPort {

  private final ExistsByIdAndUserIdOutputPort existsByIdAndUserIdOutputPort;
  private final SaveAppointmentOutputPort saveAppointmentOutputPort;
  private final FindBusinessServiceByIdOutputPort findBusinessServiceByIdOutputPort;
  private final AppointmentScheduleService appointmentScheduleService;
  private final FindActiveAppointmentByUserAndDayOutputPort findActiveAppointmentByUserAndDayOutputPort;

  private final DomainEventPublisher domainEventPublisher;

  public CreateAppointmentUseCase(
      DomainEventPublisher domainEventPublisher,
      FindActiveAppointmentByUserAndDayOutputPort findActiveAppointmentByUserAndDayOutputPort,
      AppointmentScheduleService appointmentScheduleService,
      FindBusinessServiceByIdOutputPort findBusinessServiceByIdOutputPort,
      SaveAppointmentOutputPort saveAppointmentOutputPort,
      ExistsByIdAndUserIdOutputPort existsByIdAndUserIdOutputPort
  ) {
    this.domainEventPublisher = domainEventPublisher;
    this.findActiveAppointmentByUserAndDayOutputPort = findActiveAppointmentByUserAndDayOutputPort;
    this.appointmentScheduleService = appointmentScheduleService;
    this.findBusinessServiceByIdOutputPort = findBusinessServiceByIdOutputPort;
    this.saveAppointmentOutputPort = saveAppointmentOutputPort;
    this.existsByIdAndUserIdOutputPort = existsByIdAndUserIdOutputPort;
  }

  @Override
  public Appointment execute(CreateAppointmentCommand command) {
    UUID clientId = command.clientId();

    boolean ownsClient = this.existsByIdAndUserIdOutputPort.execute(
        clientId,
        command.userId()
    );

    if (!ownsClient) {
      throw new UserDoesNotOwnException();
    }

    if (command.scheduledAt().isBefore(LocalDateTime.now())) {
      throw new InvalidScheduleDateException();
    }

    Appointment appointmentToCreate = new Appointment(
        command.scheduledAt(),
        clientId
    );

    for (ServiceItemCommand item : command.services()) {
      BusinessService service =
          findBusinessServiceByIdOutputPort.execute(item.businessServiceId());

      if (service == null) {
        throw new BusinessServiceNotFoundException(item.businessServiceId());
      }

      if (!service.getUserId().equals(command.userId())) {
        throw new UserDoesNotOwnException();
      }

      appointmentToCreate.addService(
          service,
          new Money(item.discount())
      );
    }
    List<Appointment> appointmentsOfDay = this.findActiveAppointmentByUserAndDayOutputPort.execute(
        command.userId(),
        command.scheduledAt().toLocalDate().atStartOfDay(),
        command.scheduledAt().toLocalDate().plusDays(1).atStartOfDay()
    );

    this.appointmentScheduleService.validateDatetimeNoConflict(
        appointmentToCreate,
        appointmentsOfDay
    );


    Appointment.scheduleEvent(appointmentToCreate);

    var newAppointment =
        saveAppointmentOutputPort.execute(appointmentToCreate);

    for (DomainEvent event : appointmentToCreate.pullDomainEvents()) {
      domainEventPublisher.publish(event);
    }
    return newAppointment;
  }
}
