package com.lucastexeira.authuser.common.enums;

public enum Role {
  USER("ROLE_USER"),
  ADMIN("ROLE_ADMIN");

  private final String roleName;

  Role(String roleName) {
    this.roleName = roleName;
  }

  public static Role fromString(String roleName) {
    for (Role role : Role.values()) {
      if (role.roleName.equalsIgnoreCase(roleName)) {
        return role;
      }
    }
    throw new IllegalArgumentException(
        "No enum constant for role name: " + roleName);
  }

  public String getRoleName() {
    return roleName;
  }
}
