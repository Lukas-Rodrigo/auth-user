package com.lucastexeira.authuser.adapters.out.persistence.entity.association;

import com.lucastexeira.authuser.adapters.out.persistence.entity.appointment.AppointmentEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.businesservice.BusinessServiceEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class AppointmentServiceEntity {

  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "appointment_id")
  private AppointmentEntity appointment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "business_service_id")
  private BusinessServiceEntity businessService;

  private Integer appliedPrice;
  private Integer discount;
  private Integer finalPrice;

  public AppointmentServiceEntity() {
  }

  public AppointmentServiceEntity(
      UUID id,
      AppointmentEntity appointment,
      BusinessServiceEntity businessService,
      Integer appliedPrice,
      Integer discount,
      Integer finalPrice
  ) {
    this.id = id;
    this.appointment = appointment;
    this.businessService = businessService;
    this.appliedPrice = appliedPrice;
    this.discount = discount;
    this.finalPrice = finalPrice;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public AppointmentEntity getAppointment() {
    return appointment;
  }

  public void setAppointment(AppointmentEntity appointment) {
    this.appointment = appointment;
  }

  public BusinessServiceEntity getBusinessService() {
    return businessService;
  }

  public void setBusinessService(BusinessServiceEntity businessService) {
    this.businessService = businessService;
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

  public Integer getFinalPrice() {
    return finalPrice;
  }

  public void setFinalPrice(Integer finalPrice) {
    this.finalPrice = finalPrice;
  }

  public String getServiceName() {
    return this.businessService.getName();
  }

  public BigDecimal getServicePrice() {
    return this.businessService.getPrice();
  }

  public Long getDuration() {
    return this.businessService.getDuration().toMinutes();
  }

}
