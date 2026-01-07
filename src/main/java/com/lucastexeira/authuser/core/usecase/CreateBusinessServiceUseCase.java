package com.lucastexeira.authuser.core.usecase;

import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.domain.businessservices.BusinessService;
import com.lucastexeira.authuser.core.exception.UserNotFoundException;
import com.lucastexeira.authuser.core.port.in.businessservice.CreateBusinessServiceInputPort;
import com.lucastexeira.authuser.core.port.out.businessservices.SaveBusinessServiceOutputPort;
import com.lucastexeira.authuser.core.port.out.user.FindUserByIdOutputPort;
import com.lucastexeira.authuser.core.usecase.command.CreateBusinessServiceCommand;

public class CreateBusinessServiceUseCase implements
    CreateBusinessServiceInputPort {

  private final FindUserByIdOutputPort findUserByIdOutputPort;

  private final SaveBusinessServiceOutputPort saveBusinessServiceOutputPort;

  public CreateBusinessServiceUseCase(
      FindUserByIdOutputPort findUserByIdOutputPort,
      SaveBusinessServiceOutputPort saveBusinessServiceOutputPort
  ) {
    this.findUserByIdOutputPort = findUserByIdOutputPort;
    this.saveBusinessServiceOutputPort = saveBusinessServiceOutputPort;
  }

  @Override
  public BusinessService execute(CreateBusinessServiceCommand command) {

    User isValidUser = findUserByIdOutputPort.execute(command.userId());

    if (isValidUser == null) {
      throw new UserNotFoundException(command.userId());
    }

    var newBusinessService = new BusinessService(
        command.userId(),
        command.name(),
        command.price(),
        command.duration()
    );
    return saveBusinessServiceOutputPort.execute(newBusinessService);
  }
}
