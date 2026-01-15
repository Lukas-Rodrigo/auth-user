package com.lucastexeira.authuser.core.domain.appointment.event;

import com.lucastexeira.authuser.common.event.DomainEvent;
import com.lucastexeira.authuser.core.domain.appointment.Appointment;

import java.time.Instant;

public class AppointmentStatusEvent implements DomainEvent {

  private final Appointment appointment;
  private final Instant occurredOn;

  public AppointmentStatusEvent(
      Appointment appointment,
      Instant occurredOn
  ) {
    this.appointment = appointment;
    this.occurredOn = occurredOn;
  }

  @Override
  public Instant occurredOn() {
    return this.occurredOn;
  }

  public Instant getOccurredOn() {
    return this.occurredOn;
  }

  public Appointment getAppointment() {
    return this.appointment;
  }




}
