package com.lucastexeira.authuser.core.usecase;

import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.domain.businessservices.BusinessService;
import com.lucastexeira.authuser.core.exception.BusinessServiceNotFoundException;
import com.lucastexeira.authuser.core.exception.UserNotFoundException;
import com.lucastexeira.authuser.core.port.in.businessservice.PatchBusinessServiceInputPort;
import com.lucastexeira.authuser.core.port.out.businessservices.FindBusinessServiceByIdOutputPort;
import com.lucastexeira.authuser.core.port.out.businessservices.SaveBusinessServiceOutputPort;
import com.lucastexeira.authuser.core.port.out.user.FindUserByIdOutputPort;
import com.lucastexeira.authuser.core.usecase.command.PatchBusinessServiceCommand;

import java.util.UUID;

public class PatchBusinessServiceUseCase implements
    PatchBusinessServiceInputPort {

  private final FindUserByIdOutputPort findUserByIdOutputPort;

  private final FindBusinessServiceByIdOutputPort findBusinessServiceByIdOutputPort;

  private final SaveBusinessServiceOutputPort saveBusinessServiceOutputPort;



  public PatchBusinessServiceUseCase(
      SaveBusinessServiceOutputPort saveBusinessServiceOutputPort,
      FindBusinessServiceByIdOutputPort findBusinessServiceByIdOutputPort,
      FindUserByIdOutputPort findUserByIdOutputPort
  ) {

    this.saveBusinessServiceOutputPort = saveBusinessServiceOutputPort;
    this.findBusinessServiceByIdOutputPort = findBusinessServiceByIdOutputPort;
    this.findUserByIdOutputPort = findUserByIdOutputPort;
  }

  @Override
  public BusinessService execute(UUID userId,
                                 PatchBusinessServiceCommand command) {

    User isValidUser = findUserByIdOutputPort.execute(userId);

    BusinessService updateBusinessService =
        findBusinessServiceByIdOutputPort.execute(command.id());

    if (updateBusinessService == null) {
      throw new BusinessServiceNotFoundException(command.id());
    }

    if (isValidUser == null) {
      throw new UserNotFoundException(userId);
    }

    if (command.name() != null) {
      updateBusinessService.updateName(command.name());

    }

    if (command.duration() != null) {
      updateBusinessService.updateDuration(command.duration());
    }


    if (command.price() != null) {
      updateBusinessService.updatePrice(command.price());
    }
    return saveBusinessServiceOutputPort.execute(updateBusinessService);
  }
}
