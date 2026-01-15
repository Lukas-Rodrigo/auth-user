package com.lucastexeira.authuser.core.domain.whasapp;

import com.lucastexeira.authuser.common.enums.WhatsappSessionStatus;

import java.time.LocalDate;
import java.util.UUID;

public class WhatsappSession {

  private UUID id;
  private UUID userId;
  private WhatsappSessionStatus status;
  private LocalDate connectedAt;
  private LocalDate disconnectedAt;
  private LocalDate createdAt;

  public WhatsappSession(UUID user) {
    this.userId = user;
    this.status = WhatsappSessionStatus.QR_GENERATED;
    this.connectedAt = LocalDate.now();
    this.createdAt = LocalDate.now();
  }

  public void updateStats(WhatsappSessionStatus status) {


    this.status = status;
  }

  public WhatsappSession() {}

  public Boolean isConnected() {
    return this.status == WhatsappSessionStatus.CONNECTED;
  }

  public Boolean isQrGenerated() {
    return this.status == WhatsappSessionStatus.QR_GENERATED;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }


  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
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
