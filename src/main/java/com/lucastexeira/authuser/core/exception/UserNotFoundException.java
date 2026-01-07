package com.lucastexeira.authuser.core.exception;

import java.util.UUID;

public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException(UUID user) {
    super("User with id " + user + " not found.");
  }
}
