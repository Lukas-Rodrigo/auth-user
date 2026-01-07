package com.lucastexeira.authuser.core.port.out.appointment;

import com.lucastexeira.authuser.core.domain.appointment.Appointment;

import java.util.UUID;

public interface FindAppointmentByIdOutputPort {
  Appointment execute(UUID appointmentId);
}
