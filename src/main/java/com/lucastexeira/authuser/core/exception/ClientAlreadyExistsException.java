package com.lucastexeira.authuser.core.exception;

public class ClientAlreadyExistsException extends ConflictException {
  public ClientAlreadyExistsException(String message) {
    super(message);
  }
}
