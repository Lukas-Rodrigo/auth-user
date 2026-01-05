package com.lucastexeira.authuser.core.usecase;

import com.lucastexeira.authuser.core.domain.Client;
import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.exception.ClientAlreadyExistsException;
import com.lucastexeira.authuser.core.exception.UserNotFoundException;
import com.lucastexeira.authuser.core.port.in.client.CreateClientInputPort;
import com.lucastexeira.authuser.core.port.out.client.FindClientByPhoneNumberAndNameAndUserIdOutputPort;
import com.lucastexeira.authuser.core.port.out.client.SaveClientOutputPort;
import com.lucastexeira.authuser.core.port.out.user.FindUserByIdOutputPort;
import com.lucastexeira.authuser.core.usecase.command.CreateClientCommand;

import java.util.UUID;

public class CreateClientUseCase implements CreateClientInputPort {

  private final FindUserByIdOutputPort findUserByIdOutputPort;

  private final FindClientByPhoneNumberAndNameAndUserIdOutputPort findClientByPhoneNumberAndNameAndUserIdOutputPort;

  private final SaveClientOutputPort saveClientOutputPort;

  public CreateClientUseCase(
      FindUserByIdOutputPort findUserByIdOutputPort,
      FindClientByPhoneNumberAndNameAndUserIdOutputPort findClientByPhoneNumberAndNameAndUserIdOutputPort,
      SaveClientOutputPort saveClientOutputPort
  ) {
    this.findUserByIdOutputPort = findUserByIdOutputPort;
    this.findClientByPhoneNumberAndNameAndUserIdOutputPort = findClientByPhoneNumberAndNameAndUserIdOutputPort;
    this.saveClientOutputPort = saveClientOutputPort;
  }

  @Override
  public Client execute(
      UUID userId,
      CreateClientCommand command
  ) {
    User isValidUser = findUserByIdOutputPort.execute(userId);

    if (isValidUser == null) {
      throw new UserNotFoundException(userId);
    }

    var existingClient =
        this.findClientByPhoneNumberAndNameAndUserIdOutputPort.execute(
            userId,
            command.phoneNumber(),
            command.name()
        );

    if(existingClient != null) {
      throw new ClientAlreadyExistsException(
          "Client with phone number " + command.phoneNumber() +
              " and name " + command.name() + " already exists for this user."
      );
    }

    Client newClient = new Client(
        userId,
        command.name(),
        command.phoneNumber(),
        command.profileUrl(),
        command.observations()
    );

    return this.saveClientOutputPort.execute(newClient);

  }

}
