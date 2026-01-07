package com.lucastexeira.authuser.core.usecase.businesservice;

import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.exception.UserNotFoundException;
import com.lucastexeira.authuser.core.port.in.businessservice.DeleteBusinessServiceByIdInputPort;
import com.lucastexeira.authuser.core.port.out.businessservices.DeleteBusinessServiceByIdOutputPort;
import com.lucastexeira.authuser.core.port.out.user.FindUserByIdOutputPort;

import java.util.UUID;

public class DeleteBusinessServiceByIdUseCase implements
    DeleteBusinessServiceByIdInputPort {

  private final FindUserByIdOutputPort findUserByIdOutputPort;

  private final DeleteBusinessServiceByIdOutputPort deleteBusinessServiceByIdOutputPort;


  public DeleteBusinessServiceByIdUseCase(
      FindUserByIdOutputPort findUserByIdOutputPort,
      DeleteBusinessServiceByIdOutputPort deleteBusinessServiceByIdOutputPort
  ) {
    this.findUserByIdOutputPort = findUserByIdOutputPort;
    this.deleteBusinessServiceByIdOutputPort = deleteBusinessServiceByIdOutputPort;
  }

  @Override
  public void execute(
      UUID userId,
      UUID businessServiceId
  ) {

    User isValidUser = findUserByIdOutputPort.execute(userId);

    if (isValidUser == null) {
      throw new UserNotFoundException(userId);
    }

    this.deleteBusinessServiceByIdOutputPort.execute(businessServiceId);

  }
}
