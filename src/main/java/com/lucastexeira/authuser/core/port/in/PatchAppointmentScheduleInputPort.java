package com.lucastexeira.authuser.core.port.in;

import com.lucastexeira.authuser.core.domain.appointment.Appointment;
import com.lucastexeira.authuser.core.usecase.command.PatchAppointmentScheduleCommand;

public interface PatchAppointmentScheduleInputPort {
  Appointment execute(PatchAppointmentScheduleCommand command);
}
