package com.lucastexeira.authuser.core.usecase.client;

import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.exception.UserNotFoundException;
import com.lucastexeira.authuser.core.port.in.client.DeleteClientByIdInputPort;
import com.lucastexeira.authuser.core.port.out.client.DeleteClientByIdOutputPort;
import com.lucastexeira.authuser.core.port.out.user.FindUserByIdOutputPort;

import java.util.UUID;

public class DeleteClientByIdUseCase implements DeleteClientByIdInputPort {

  private final FindUserByIdOutputPort findUserByIdOutputPort;

  private final DeleteClientByIdOutputPort deleteClientByIdOutputPort;


  public DeleteClientByIdUseCase(
      FindUserByIdOutputPort findUserByIdOutputPort,
      DeleteClientByIdOutputPort deleteClientByIdOutputPort
  ) {
    this.findUserByIdOutputPort = findUserByIdOutputPort;
    this.deleteClientByIdOutputPort = deleteClientByIdOutputPort;
  }

  @Override
  public void execute(
      UUID userId,
      UUID clientId
  ) {

    User isValidUser = findUserByIdOutputPort.execute(userId);

    if (isValidUser == null) {
      throw new UserNotFoundException(userId);
    }

    deleteClientByIdOutputPort.execute(clientId);
  }
}
