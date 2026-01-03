package com.lucastexeira.authuser.core.exception;

import java.util.UUID;

public class AppointmentNotFoundException extends NotFoundException {
  public AppointmentNotFoundException(UUID appointmentId) {
    super("Appointment with ID " + appointmentId + " not found.");
  }
}
