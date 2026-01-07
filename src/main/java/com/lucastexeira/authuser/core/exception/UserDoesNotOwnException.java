package com.lucastexeira.authuser.core.exception;

public class UserDoesNotOwnException extends ForbiddenException {
  public UserDoesNotOwnException() {
    super("User does not own the client for this appointment");
  }
}
