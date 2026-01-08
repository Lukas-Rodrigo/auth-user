package com.lucastexeira.authuser.adapters.out.persistence.entity.client;

import com.lucastexeira.authuser.adapters.out.persistence.entity.user.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "clients")
@SQLDelete(sql = "UPDATE clients SET deleted_at = CURRENT_DATE WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class ClientEntity {


  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String phoneNumber;

  @Column(name = "profile_url")
  private String profileUrl;

  @Column(columnDefinition = "TEXT")
  private String observations;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDate createdAt;

  @Column(name = "deleted_at")
  private LocalDate deletedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  public ClientEntity() {
  }

  public ClientEntity(
      UUID id,
      String name,
      String phoneNumber,
      String profileUrl,
      String observations,
      LocalDate createdAt,
      LocalDate deletedAt,
      UserEntity user
  ) {
    this.id = id;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.profileUrl = profileUrl;
    this.observations = observations;
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

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }
}
