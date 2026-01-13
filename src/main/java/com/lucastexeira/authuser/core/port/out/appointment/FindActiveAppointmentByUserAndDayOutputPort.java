package com.lucastexeira.authuser.core.port.out.appointment;

import com.lucastexeira.authuser.core.domain.appointment.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface FindActiveAppointmentByUserAndDayOutputPort {

  List<Appointment> execute(
      UUID userId,
      LocalDateTime startOfDay,
      LocalDateTime endOfDay
  );
}
