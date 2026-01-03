package com.lucastexeira.authuser.core.domain.appointment.event;

import com.lucastexeira.authuser.common.event.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class AppointmentStatusEvent implements DomainEvent {

  private final UUID appointmentId;
  private final Instant scheduledAt;
  private final Instant occurredOn;

  public AppointmentStatusEvent(
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
    return this.occurredOn;
  }

  public UUID getAppointmentId() {
    return this.appointmentId;
  }

  public Instant getScheduledAt() {
    return this.scheduledAt;
  }

  public Instant getOccurredOn() {
    return this.occurredOn;
  }


}
