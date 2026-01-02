package com.lucastexeira.authuser.core.usecase.appointment;

import com.lucastexeira.authuser.core.domain.appointment.Appointment;
import com.lucastexeira.authuser.core.port.in.PatchAppointmentScheduleInputPort;
import com.lucastexeira.authuser.core.port.out.appointment.FindAppointmentByIdOutputPort;
import com.lucastexeira.authuser.core.port.out.appointment.SaveAppointmentOutputPort;
import com.lucastexeira.authuser.core.usecase.command.PatchAppointmentScheduleCommand;

public class PatchAppointmentScheduleUseCase implements
    PatchAppointmentScheduleInputPort {

  private final FindAppointmentByIdOutputPort findAppointmentByIdOutputPort;

  private final SaveAppointmentOutputPort saveAppointmentOutputPort;

  public PatchAppointmentScheduleUseCase(
      FindAppointmentByIdOutputPort findAppointmentByIdOutputPort,
      SaveAppointmentOutputPort saveAppointmentOutputPort
  ) {
    this.findAppointmentByIdOutputPort = findAppointmentByIdOutputPort;
    this.saveAppointmentOutputPort = saveAppointmentOutputPort;
  }

  @Override
  public Appointment execute(PatchAppointmentScheduleCommand command) {
    Appointment isValidAppointment =
        findAppointmentByIdOutputPort.execute(command.appointmentId());

    if (isValidAppointment == null) {
      throw new RuntimeException("Appointment not found");
    }

    isValidAppointment.reschedule(command.scheduleDateTime());

    return saveAppointmentOutputPort.execute(isValidAppointment);

  }
}
