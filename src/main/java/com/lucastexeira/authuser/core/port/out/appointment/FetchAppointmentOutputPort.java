package com.lucastexeira.authuser.core.port.out.appointment;

import com.lucastexeira.authuser.core.domain.appointment.Appointment;
import com.lucastexeira.authuser.core.usecase.result.AppointmentDetailsResult;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;

import java.time.LocalDate;
import java.util.UUID;

public interface FetchAppointmentOutputPort {

  PageGenericResult<AppointmentDetailsResult> execute(
      UUID userId,
      int page,
      int size,
      LocalDate startDate,
      LocalDate endDate
  );
}
