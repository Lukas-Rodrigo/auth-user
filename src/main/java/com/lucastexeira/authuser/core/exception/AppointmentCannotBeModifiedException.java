package com.lucastexeira.authuser.core.exception;

public class AppointmentCannotBeModifiedException extends DomainException {
  public AppointmentCannotBeModifiedException() {
    super("Appointment can not be modified in its current status.");
  }
}
