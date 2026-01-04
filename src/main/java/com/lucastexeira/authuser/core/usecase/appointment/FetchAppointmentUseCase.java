package com.lucastexeira.authuser.core.usecase.appointment;

import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.domain.appointment.Appointment;
import com.lucastexeira.authuser.core.exception.UserNotFoundException;
import com.lucastexeira.authuser.core.port.in.FetchAppointmentInputPort;
import com.lucastexeira.authuser.core.port.out.appointment.FetchAppointmentOutputPort;
import com.lucastexeira.authuser.core.port.out.user.FindUserByIdOutputPort;
import com.lucastexeira.authuser.core.usecase.command.FetchAppointmentCommand;
import com.lucastexeira.authuser.core.usecase.result.AppointmentDetailsResult;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;

public class FetchAppointmentUseCase implements FetchAppointmentInputPort {

  private final FetchAppointmentOutputPort fetchAppointmentOutputPort;

  private final FindUserByIdOutputPort findUserByIdOutputPort;
  public FetchAppointmentUseCase(FetchAppointmentOutputPort fetchAppointmentOutputPort, FindUserByIdOutputPort findUserByIdOutputPort) {
    this.fetchAppointmentOutputPort = fetchAppointmentOutputPort;
    this.findUserByIdOutputPort = findUserByIdOutputPort;
  }

  @Override
  public PageGenericResult<AppointmentDetailsResult> execute(FetchAppointmentCommand command) {

    User isValidUser = findUserByIdOutputPort.execute(command.userId());

    if (isValidUser == null) {
      throw new UserNotFoundException(command.userId());
    }

    return fetchAppointmentOutputPort.execute(
        command.userId(),
        command.query().page(),
        command.query().size(),
        command.query().startDate(),
        command.query().endDate()
    );

  }
}
