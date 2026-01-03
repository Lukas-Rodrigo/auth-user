package com.lucastexeira.authuser.common.enums;

public enum AppointmentStatus {
  PENDING("PENDING"),
  CONFIRMED("CONFIRMED"),
  COMPLETED("COMPLETED"),
  CANCELED("CANCELED");

  private final String status;

  AppointmentStatus(String status) {
    this.status = status;
  }

  public static AppointmentStatus fromString(String status) {
    for (AppointmentStatus appointmentStatus : AppointmentStatus.values()) {
      if (appointmentStatus.status.equalsIgnoreCase(status)) {
        return appointmentStatus;
      }
    }
    throw new IllegalArgumentException(
        "No enum constant for status: " + status);
  }

  public String getStatus() {
    return status;
  }


}
