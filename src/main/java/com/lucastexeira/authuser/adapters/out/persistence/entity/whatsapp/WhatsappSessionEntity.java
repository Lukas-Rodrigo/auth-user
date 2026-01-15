package com.lucastexeira.authuser.adapters.out.persistence.entity.whatsapp;

import com.lucastexeira.authuser.adapters.out.persistence.entity.user.UserEntity;
import com.lucastexeira.authuser.common.enums.WhatsappSessionStatus;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "whatsapp_session")
public class WhatsappSessionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private WhatsappSessionStatus status;

  @Column(name = "connected_at")
  private LocalDate connectedAt;

  @Column(name = "disconnected_at")
  private LocalDate disconnectedAt;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDate createdAt;

  public WhatsappSessionEntity() {}

  public WhatsappSessionEntity(
      UUID id,
      UserEntity user,
      WhatsappSessionStatus status,
      LocalDate connectedAt,
      LocalDate disconnectedAt,
      LocalDate createdAt
  ) {
    this.id = id;
    this.user = user;
    this.status = status;
    this.connectedAt = connectedAt;
    this.disconnectedAt = disconnectedAt;
    this.createdAt = createdAt;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public WhatsappSessionStatus getStatus() {
    return status;
  }

  public void setStatus(WhatsappSessionStatus status) {
    this.status = status;
  }

  public LocalDate getConnectedAt() {
    return connectedAt;
  }

  public void setConnectedAt(LocalDate connectedAt) {
    this.connectedAt = connectedAt;
  }

  public LocalDate getDisconnectedAt() {
    return disconnectedAt;
  }

  public void setDisconnectedAt(LocalDate disconnectedAt) {
    this.disconnectedAt = disconnectedAt;
  }

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }
}
