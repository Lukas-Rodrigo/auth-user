package com.lucastexeira.authuser.core.service;

import com.lucastexeira.authuser.core.domain.appointment.Appointment;
import com.lucastexeira.authuser.core.exception.AppointmentConflictException;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentScheduleService {

  public void validateDatetimeNoConflict(
      Appointment newAppointment,
      List<Appointment> existingAppointments
  ) {
    // start of appointment created
    LocalDateTime newStart = newAppointment.getScheduledAt();

    // end of appointment created
    LocalDateTime newEnd = newStart.plus(newAppointment.getTotalDuration());

    for (Appointment existing : existingAppointments) {
      LocalDateTime existingStart = existing.getScheduledAt();
      LocalDateTime existingEnd =
          existingStart.plus(existing.getTotalDuration());

      boolean isConflict =
          newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart);

      if (isConflict) {
        throw new AppointmentConflictException();
      }
    }

  }
}
