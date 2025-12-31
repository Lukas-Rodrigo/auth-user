package com.lucastexeira.authuser.core.usecase;

import com.lucastexeira.authuser.core.exception.UserAlreadyExistsException;
import com.lucastexeira.authuser.core.exception.UserNotFoundException;
import com.lucastexeira.authuser.core.port.in.CreateUserInputPort;
import com.lucastexeira.authuser.core.port.out.CreateUserOutputPort;
import com.lucastexeira.authuser.core.port.out.FindUserByEmailOutputPort;
import com.lucastexeira.authuser.domain.User;

public class CreateUserUseCase implements CreateUserInputPort {

  private final CreateUserOutputPort createUserOutputPort;
  private final FindUserByEmailOutputPort findUserByEmailOutputPort;


  public CreateUserUseCase(CreateUserOutputPort createUserOutputPort, FindUserByEmailOutputPort findUserByEmailOutputPort) {
    this.createUserOutputPort = createUserOutputPort;
    this.findUserByEmailOutputPort = findUserByEmailOutputPort;
  }

  @Override
  public User createUser(User user) {
    var existingUser = findUserByEmailOutputPort.find(user.getEmail());

    if(existingUser.isPresent()) {
      throw new UserAlreadyExistsException("User with email " + user.getEmail() + " already exists.");
    }

    return createUserOutputPort.createUser(user);

  }
}
