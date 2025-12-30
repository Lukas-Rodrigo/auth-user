package com.lucastexeira.authuser.adapters.out.persistence.entity.user;

import com.lucastexeira.authuser.common.enums.Role;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;


  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDate createdAt;



  public UserEntity() {
  }

  public UserEntity(UUID id, String name, String password, String email, LocalDateTime createdAt) {
    this.id = id;
    this.name = name;
    this.password = password;
    this.email = email;
    createdAt = createdAt;
  }

  @PrePersist
  private void onCreate() {
    if (this.createdAt == null) {
      this.createdAt = LocalDate.now();
    }
  }


  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    createdAt = createdAt;
  }
}
