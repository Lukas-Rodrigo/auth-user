package com.lucastexeira.authuser.adapters.out.persistence.entity.appointment;

import com.lucastexeira.authuser.adapters.out.persistence.entity.association.AppointmentServiceEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.client.ClientEntity;
import com.lucastexeira.authuser.common.enums.AppointmentStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class AppointmentEntity {

  @Id
  private UUID id;

  @Column(name = "scheduled_at", nullable = false)
  private LocalDateTime scheduledAt;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AppointmentStatus status;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDate createdAt;

  @Column(name = "updated_at")
  private LocalDate updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id", nullable = false)
  private ClientEntity client;


  @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AppointmentServiceEntity> services;

  @Column(name = "total_price")
  private Integer totalPrice;

  public AppointmentEntity() {

  }

  public AppointmentEntity(
      UUID id,
      LocalDateTime scheduledAt,
      AppointmentStatus status,
      LocalDate createdAt,
      LocalDate updatedAt,
      ClientEntity client,
      List<AppointmentServiceEntity> services
  ) {
    this.id = id;
    this.scheduledAt = scheduledAt;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.client = client;
    this.services = services;
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

  public ClientEntity getClient() {
    return client;
  }

  public void setClient(ClientEntity client) {
    this.client = client;
  }

  public List<AppointmentServiceEntity> getServices() {
    return services;
  }

  public void setServices(List<AppointmentServiceEntity> services) {
    this.services = services;
  }

  public Integer getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Integer totalPrice) {
    this.totalPrice = totalPrice;
  }
}
