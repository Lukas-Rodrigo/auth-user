package com.lucastexeira.authuser.core.exception;

public class UserAlreadyExistsException extends ConflictException {
  public UserAlreadyExistsException(String message) {
    super(message);
  }
}
