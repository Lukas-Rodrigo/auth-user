package com.lucastexeira.authuser.common.enums;

public enum WhatsappSessionStatus {
  CONNECTED("CONNECTED"),
  DISCONNECTED("DISCONNECTED"),
  QR_GENERATED("QR_GENERATED");

  private final String status;

  WhatsappSessionStatus(String status) {
    this.status = status;
  }

  public static WhatsappSessionStatus fromString(String status) {
    for (WhatsappSessionStatus session : WhatsappSessionStatus.values()) {
      if (session.status.equalsIgnoreCase(status)) {
        return session;
      }
    }
    throw new IllegalArgumentException(
        "No enum constant for status: " + status);
  }


}
