package com.lucastexeira.authuser.core.usecase;

import com.lucastexeira.authuser.core.domain.Client;
import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.exception.ClientAlreadyExistsException;
import com.lucastexeira.authuser.core.exception.ClientNotFoundException;
import com.lucastexeira.authuser.core.exception.UserNotFoundException;
import com.lucastexeira.authuser.core.port.in.client.FindClientByIdOutputPort;
import com.lucastexeira.authuser.core.port.in.client.PatchClientInputPort;
import com.lucastexeira.authuser.core.port.out.client.FindClientByPhoneNumberAndNameAndUserIdOutputPort;
import com.lucastexeira.authuser.core.port.out.client.SaveClientOutputPort;
import com.lucastexeira.authuser.core.port.out.user.FindUserByIdOutputPort;
import com.lucastexeira.authuser.core.usecase.command.PatchClientCommand;

import java.util.UUID;

public class PatchClientUseCase implements PatchClientInputPort {


  private final FindUserByIdOutputPort findUserByIdOutputPort;

  private final FindClientByIdOutputPort findClientByIdOutputPort;

  private final FindClientByPhoneNumberAndNameAndUserIdOutputPort findClientByPhoneNumberAndNameAndUserIdOutputPort;

  private final SaveClientOutputPort saveClientOutputPort;


  public PatchClientUseCase(
      FindUserByIdOutputPort findUserByIdOutputPort,
      FindClientByIdOutputPort findClientByIdOutputPort,
      FindClientByPhoneNumberAndNameAndUserIdOutputPort findClientByPhoneNumberAndNameAndUserIdOutputPort,
      SaveClientOutputPort saveClientOutputPort
  ) {
    this.findUserByIdOutputPort = findUserByIdOutputPort;
    this.findClientByIdOutputPort = findClientByIdOutputPort;
    this.findClientByPhoneNumberAndNameAndUserIdOutputPort = findClientByPhoneNumberAndNameAndUserIdOutputPort;
    this.saveClientOutputPort = saveClientOutputPort;
  }

  @Override
  public Client execute(
      UUID userId,
      PatchClientCommand command
  ) {

    User isValidUser = findUserByIdOutputPort.execute(userId);

    if (isValidUser == null) {
      throw new UserNotFoundException(userId);
    }

    Client ClientToBePatched = findClientByIdOutputPort.execute(command.id());

    if (ClientToBePatched == null) {
      throw new ClientNotFoundException(command.id());
    }

    if (command.name() != null || command.phoneNumber() != null) {
      String newName = command.name() != null ? command.name() : ClientToBePatched.getName();
      String newPhoneNumber = command.phoneNumber() != null ? command.phoneNumber() : ClientToBePatched.getPhoneNumber();

      this.verifyPhoneNumberAndNameUnique(
          userId,
          ClientToBePatched.getId(),
          newPhoneNumber,
          newName
      );
      ClientToBePatched.updateName(newName);
      ClientToBePatched.updatePhoneNumber(newPhoneNumber);
    }

    if (command.observations() != null) {
      ClientToBePatched.updateObservations(command.observations());
    }

    if (command.profileUrl() != null) {
      ClientToBePatched.updateProfileUrl(command.profileUrl());
    }

    return saveClientOutputPort.execute(ClientToBePatched);
  }

  private void verifyPhoneNumberAndNameUnique(
      UUID userId,
      UUID clientId,
      String phoneNumber,
      String name
  ) {
    Client existingClient =
        findClientByPhoneNumberAndNameAndUserIdOutputPort
            .execute(userId,phoneNumber, name);

    if (existingClient != null && !existingClient.getId().equals(clientId)) {
      throw new ClientAlreadyExistsException(
          "Client with phone number " + phoneNumber +
              " and name " + name + " already exists for this user."
      );
    }
  }
}
