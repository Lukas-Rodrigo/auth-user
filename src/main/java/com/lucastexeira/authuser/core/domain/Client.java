package com.lucastexeira.authuser.core.domain;


import java.time.LocalDate;
import java.util.UUID;

public class Client {

  private UUID id;

  private String name;

  private String phoneNumber;

  private String profileUrl;

  private String observations;

  private LocalDate createdAt;

  private LocalDate deletedAt;

  private UUID userId;

  public Client() {

  }

  public Client(
      UUID id,
      String name,
      String phoneNumber,
      String profileUrl,
      String observations,
      LocalDate createdAt,
      LocalDate deletedAt,
      UUID userId
  ) {
    this.id = id;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.profileUrl = profileUrl;
    this.observations = observations;
    this.createdAt = createdAt;
    this.deletedAt = deletedAt;
    this.userId = userId;
  }

  public boolean belongsTo(UUID userId) {
    return this.userId.equals(userId);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getProfileUrl() {
    return profileUrl;
  }

  public void setProfileUrl(String profileUrl) {
    this.profileUrl = profileUrl;
  }

  public String getObservations() {
    return observations;
  }

  public void setObservations(String observations) {
    this.observations = observations;
  }

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDate getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(LocalDate deletedAt) {
    this.deletedAt = deletedAt;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }
}
