package com.lucastexeira.authuser.core.domain.businessservices;

import com.lucastexeira.authuser.core.domain.valueobject.Money;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

public class BusinessService {

  private UUID id;
  private UUID userId;

  private String name;
  private Money price;
  private Duration duration;

  private LocalDate createdAt;
  private LocalDate deletedAt;

  public BusinessService() {
  }

  public BusinessService(
      UUID userId,
      String name,
      Money price,
      Duration duration
  ) {
    if (duration == null || duration.isZero() || duration.isNegative()) {
      throw new IllegalArgumentException("Duration must be greater than zero");
    }

    this.userId = userId;
    this.name = name;
    this.price = price;
    this.duration = duration;
    this.createdAt = LocalDate.now();
  }


  public BusinessService(
      UUID id,
      UUID userId,
      String name,
      Money price,
      Duration duration,
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

  public void updateName(String name) {

    if (this.name.equals(name)) {
      return;
    }

    if (name != null && !name.isBlank()) {
      this.name = name;
    }
  }

  public void updatePrice(Money price) {

    if (this.price.equals(price)) {
      return;
    }

    this.price.add(price);
  }

  public void updateDuration(Duration newDuration) {

    if (newDuration == null || newDuration.isZero() || newDuration.isNegative()) {
      throw new IllegalArgumentException("Duration must be greater than zero");
    }

    if (this.duration.equals(newDuration)) {
      return;
    }

    this.duration = newDuration;
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


  public Money getPrice() {
    return price;
  }

  public void setPrice(Money price) {
    this.price = price;
  }

  public Duration getDuration() {
    return duration;
  }

  public void setDuration(Duration duration) {
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
