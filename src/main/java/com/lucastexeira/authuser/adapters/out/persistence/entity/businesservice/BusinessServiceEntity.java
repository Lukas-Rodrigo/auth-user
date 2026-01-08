package com.lucastexeira.authuser.adapters.out.persistence.entity.businesservice;

import com.lucastexeira.authuser.adapters.out.persistence.entity.user.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "business_services")
@SQLDelete(sql = "UPDATE business_services SET deleted_at = CURRENT_DATE WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class BusinessServiceEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private BigDecimal price;

  @Column(nullable = false)
  private Duration duration;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDate createdAt;

  @Column(name = "deleted_at")
  private LocalDate deletedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  public BusinessServiceEntity() {
  }

  public BusinessServiceEntity(
      UUID id,
      String name,
      BigDecimal price,
      Duration duration,
      LocalDate createdAt,
      LocalDate deletedAt,
      UserEntity user
  ) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.duration = duration;
    this.createdAt = createdAt;
    this.deletedAt = deletedAt;
    this.user = user;
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

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
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

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }
}
