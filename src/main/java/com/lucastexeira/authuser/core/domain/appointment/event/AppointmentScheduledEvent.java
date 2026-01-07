package com.lucastexeira.authuser.core.domain.appointment.event;

import com.lucastexeira.authuser.common.event.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class AppointmentScheduledEvent implements DomainEvent {


  private final UUID appointmentId;
  private final Instant scheduledAt;
  private final Instant occurredOn;

  public AppointmentScheduledEvent(
      UUID appointmentId,
      Instant scheduledAt,
      Instant occurredOn
  ) {
    this.appointmentId = appointmentId;
    this.scheduledAt = scheduledAt;
    this.occurredOn = occurredOn;
  }

  @Override
  public Instant occurredOn() {
    return occurredOn;
  }

  public UUID getAppointmentId() {
    return appointmentId;
  }


  public Instant getScheduledAt() {
    return scheduledAt;
  }
}
