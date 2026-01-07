package com.lucastexeira.authuser.core.exception;

public class InvalidRefreshTokenException extends DomainException {
  public InvalidRefreshTokenException(String message) {
    super(message);
  }
}
