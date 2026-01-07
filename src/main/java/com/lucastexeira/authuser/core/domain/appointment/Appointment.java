package com.lucastexeira.authuser.core.domain.appointment;


import com.lucastexeira.authuser.common.enums.AppointmentStatus;
import com.lucastexeira.authuser.common.event.DomainEvent;
import com.lucastexeira.authuser.core.domain.appointment.event.AppointmentScheduledEvent;
import com.lucastexeira.authuser.core.domain.businessservices.BusinessService;
import com.lucastexeira.authuser.core.exception.AppointmentCannotBeModifiedException;
import com.lucastexeira.authuser.core.exception.BusinessServiceNotFoundException;
import com.lucastexeira.authuser.core.exception.InvalidScheduleDateException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Appointment {


  private final List<DomainEvent> domainEvents = new ArrayList<>();
  private UUID id;
  private LocalDateTime scheduledAt;
  private AppointmentStatus status;
  private LocalDate createdAt;
  private LocalDate updatedAt;
  private UUID clientId;
  private List<AppointmentService> services;
  private Integer totalPrice;

  public Appointment() {
  }

  public Appointment(
      LocalDateTime scheduledAt,
      UUID clientId
  ) {
    this.id = UUID.randomUUID();
    this.scheduledAt = scheduledAt;
    this.createdAt = LocalDate.now();
    this.clientId = clientId;
    this.services = new ArrayList<>();
    this.status = AppointmentStatus.PENDING;
  }

  public static Appointment schedule(Appointment appointment) {
    appointment.registerEvent(
        new AppointmentScheduledEvent(appointment.id, Instant.now()
            , Instant.now())
    );
    return appointment;
  }


  private void registerEvent(DomainEvent event) {
    this.domainEvents.add(event);
  }

  public List<DomainEvent> pullDomainEvents() {
    List<DomainEvent> events = List.copyOf(domainEvents);
    domainEvents.clear();
    return events;
  }

  public boolean belongsToClient(UUID clientId) {
    return this.clientId.equals(clientId);
  }

  public void addService(
      BusinessService service,
      Integer discount
  ) {
    if (service == null) {
      throw new IllegalArgumentException("service must not be null");
    }
    AppointmentService appointmentService = new AppointmentService(
        null,
        service.getId(),
        service.getPrice(),
        discount
    );
    services.add(appointmentService);
    this.totalPrice();
  }

  public void clearServices() {
    if (!canBeModified()) {
      throw new AppointmentCannotBeModifiedException();
    }
    this.services.clear();
    this.totalPrice = 0;
  }

  void totalPrice() {
    this.totalPrice = services.stream()
        .mapToInt(service -> {
          int price = service.getAppliedPrice();
          int discount = service.getDiscount() != null ? service.getDiscount() : 0;
          return price - discount;
        })
        .sum();
  }

  boolean canBeModified() {
    return this.status != AppointmentStatus.CANCELED &&
        this.status != AppointmentStatus.COMPLETED;

  }

  public void updateStatus(AppointmentStatus status) {

    if (status == null) {
      throw new IllegalArgumentException("status must not be null");
    }

    if (!this.canBeModified()) {
      throw new AppointmentCannotBeModifiedException();
    }

    switch (status) {
      case CONFIRMED -> this.confirm();
      case CANCELED -> this.cancel();
      case COMPLETED -> this.completed();
      case PENDING -> this.setPending();
    }

    this.status = status;
  }

  public void reschedule(LocalDateTime newDateTime) {

    if (!canBeModified()) {
      throw new AppointmentCannotBeModifiedException();
    }

    if (newDateTime == null) {
      throw new IllegalArgumentException("New date time cannot be null");
    }

    if (newDateTime.isBefore(LocalDateTime.now())) {
      throw new InvalidScheduleDateException();
    }

    this.scheduledAt = newDateTime;
    this.registerEvent(new AppointmentScheduledEvent(
        this.id,
        Instant.now(),
        Instant.now()
    ));
  }

  public void confirm() {
    if (!canBeModified()) {
      throw new AppointmentCannotBeModifiedException();
    }

    if (this.status == AppointmentStatus.CONFIRMED) {
      return;
    }

    this.status = AppointmentStatus.CONFIRMED;
  }

  public void cancel() {
    if (!canBeModified()) {
      throw new AppointmentCannotBeModifiedException();
    }

    if (this.status == AppointmentStatus.CANCELED) {
      return;
    }

    this.status = AppointmentStatus.CANCELED;
  }

  public void completed() {
    if (!canBeModified()) {
      throw new AppointmentCannotBeModifiedException();
    }

    if (this.status == AppointmentStatus.COMPLETED) {
      return;
    }

    this.status = AppointmentStatus.COMPLETED;
  }

  void setPending() {
    if (!canBeModified()) {
      throw new AppointmentCannotBeModifiedException();
    }

    if (this.status == AppointmentStatus.PENDING) {
      return;
    }

    this.status = AppointmentStatus.PENDING;
  }


  public Integer getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Integer totalPrice) {
    this.totalPrice = totalPrice;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public LocalDateTime getScheduledAt() {
    return scheduledAt;
  }

  public void setScheduledAt(LocalDateTime scheduledAt) {
    this.scheduledAt = scheduledAt;
  }

  public AppointmentStatus getStatus() {
    return status;
  }

  public void setStatus(AppointmentStatus status) {
    this.status = status;
  }

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDate getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDate updatedAt) {
    this.updatedAt = updatedAt;
  }

  public UUID getClientId() {
    return clientId;
  }

  public void setClientId(UUID clientId) {
    this.clientId = clientId;
  }

  public List<AppointmentService> getServices() {
    return services;
  }

  public void setServices(List<AppointmentService> services) {
    this.services = services;
  }
}
