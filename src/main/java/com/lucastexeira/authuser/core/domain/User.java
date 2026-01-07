package com.lucastexeira.authuser.core.domain;

import com.lucastexeira.authuser.common.enums.Role;
import com.lucastexeira.authuser.core.port.out.authentication.PasswordEncoderOutputPort;

import java.time.LocalDate;
import java.util.UUID;

public class User {

  private UUID id;
  private String name;
  private String email;
  private String password;
  private LocalDate createdAt;
  private Role role;


  public User() {
  }

  public User(
      UUID id,
      String name,
      String email,
      String password,
      LocalDate createdAt
  ) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.createdAt = createdAt;
  }

  public User(
      UUID id,
      String name,
      String email,
      String password
  ) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
  }

  public boolean passwordMatches(
      String raw,
      PasswordEncoderOutputPort encoder
  ) {
    return encoder.matches(raw, this.password);
  }


  public boolean isAdmin() {
    return this.role == Role.ADMIN;
  }

  public String getId() {
    return id.toString();
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public LocalDate getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }
}
