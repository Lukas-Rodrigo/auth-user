package com.lucastexeira.authuser.core.domain.appointment.event;

import com.lucastexeira.authuser.common.event.DomainEvent;
import com.lucastexeira.authuser.core.domain.appointment.Appointment;

import java.time.Instant;
import java.util.UUID;

public class AppointmentScheduledEvent implements DomainEvent {


  private final Instant occurredOn;

  private final Appointment appointment;

  public AppointmentScheduledEvent(
      Instant occurredOn,
      Appointment appointment
  ) {
    this.occurredOn = occurredOn;
    this.appointment = appointment;
  }

  @Override
  public Instant occurredOn() {
    return occurredOn;
  }

  public Appointment getAppointment() {
    return appointment;
  }


}
