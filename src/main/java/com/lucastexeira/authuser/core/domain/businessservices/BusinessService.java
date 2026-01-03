package com.lucastexeira.authuser.core.domain.businessservices;

import java.time.LocalDate;
import java.util.UUID;

public class BusinessService {

  private UUID id;
  private UUID userId;

  private String name;
  private Integer price;
  private Integer duration;

  private LocalDate createdAt;
  private LocalDate deletedAt;

  public BusinessService() {
  }

  public BusinessService(
      UUID id,
      UUID userId,
      String name,
      Integer price,
      Integer duration,
      LocalDate createdAt,
      LocalDate deletedAt
  ) {
    this.id = id;
    this.userId = userId;
    this.name = name;
    this.price = price;
    this.duration = duration;
    this.createdAt = createdAt;
    this.deletedAt = deletedAt;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
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

  @Override
  public String toString() {
    return "BusinessService{" +
        "id=" + id +
        ", userId=" + userId +
        ", name='" + name + '\'' +
        ", price=" + price +
        ", duration=" + duration +
        ", createdAt=" + createdAt +
        ", deletedAt=" + deletedAt +
        '}';
  }
}
