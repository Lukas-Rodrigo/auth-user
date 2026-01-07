package com.lucastexeira.authuser.core.domain.appointment;

import java.util.UUID;

public class AppointmentService {

  private UUID id;

  private UUID appointmentId;

  private UUID businessServiceId;

  private Integer appliedPrice;

  private Integer discount;

  public AppointmentService() {
  }

  public AppointmentService(
      UUID id,
      UUID businessServiceId,
      Integer appliedPrice,
      Integer discount
  ) {
    this.id = id;
    this.businessServiceId = businessServiceId;
    this.appliedPrice = appliedPrice;
    this.discount = discount;
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

  public Integer getAppliedPrice() {
    return appliedPrice;
  }

  public void setAppliedPrice(Integer appliedPrice) {
    this.appliedPrice = appliedPrice;
  }

  public Integer getDiscount() {
    return discount;
  }

  public void setDiscount(Integer discount) {
    this.discount = discount;
  }
}
