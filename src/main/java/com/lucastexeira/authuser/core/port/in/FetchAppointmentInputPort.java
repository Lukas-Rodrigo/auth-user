package com.lucastexeira.authuser.core.port.in;

import com.lucastexeira.authuser.core.domain.appointment.Appointment;
import com.lucastexeira.authuser.core.usecase.command.FetchAppointmentCommand;
import com.lucastexeira.authuser.core.usecase.result.AppointmentDetailsResult;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;

public interface FetchAppointmentInputPort {
  public PageGenericResult<AppointmentDetailsResult> execute(FetchAppointmentCommand command);
}
