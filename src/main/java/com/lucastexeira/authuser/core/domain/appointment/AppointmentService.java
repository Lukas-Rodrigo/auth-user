package com.lucastexeira.authuser.core.domain.appointment;

import com.lucastexeira.authuser.core.domain.businessservices.BusinessService;
import com.lucastexeira.authuser.core.domain.valueobject.Money;

import java.time.Duration;
import java.util.UUID;

public class AppointmentService {

  private UUID id;

  private UUID appointmentId;

  private UUID businessServiceId;

  private Money appliedPrice;

  private Money discount;

  private Duration duration;

  public AppointmentService() {
  }

  public AppointmentService(
      UUID appointmentId,
      BusinessService service,
      Money discount
  ) {
    if (service == null) {
      throw new IllegalArgumentException("BusinessService must not be null");
    }

    this.appointmentId = appointmentId;
    this.businessServiceId = service.getId();
    this.duration = service.getDuration();
    this.appliedPrice = service.getPrice();
    this.discount = discount != null ? discount : Money.zero();
  }

  public Money finalPrice() {
    return appliedPrice.subtract(discount);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getAppointmentId() {
    return appointmentId;
  }

  public void setAppointmentId(UUID appointmentId) {
    this.appointmentId = appointmentId;
  }

  public UUID getBusinessServiceId() {
    return businessServiceId;
  }

  public void setBusinessServiceId(UUID businessServiceId) {
    this.businessServiceId = businessServiceId;
  }

  public Duration getDuration() {
    return duration;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  public Money getDiscount() {
    return discount;
  }

  public void setDiscount(Money discount) {
    this.discount = discount;
  }

  public Money getAppliedPrice() {
    return appliedPrice;
  }

  public void setAppliedPrice(Money appliedPrice) {
    this.appliedPrice = appliedPrice;
  }

  @Override
  public String toString() {
    return "AppointmentService{" +
        "id=" + id +
        ", appointmentId=" + appointmentId +
        ", businessServiceId=" + businessServiceId +
        ", appliedPrice=" + appliedPrice +
        ", discount=" + discount +
        ", duration=" + duration +
        '}';
  }
}
