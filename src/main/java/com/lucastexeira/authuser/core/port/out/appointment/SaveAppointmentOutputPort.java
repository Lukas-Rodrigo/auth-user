package com.lucastexeira.authuser.core.port.out.appointment;

import com.lucastexeira.authuser.core.domain.appointment.Appointment;

public interface SaveAppointmentOutputPort {

  Appointment execute(Appointment appointment);
}
