package com.lucastexeira.authuser.core.exception;

public class WrongCredentialsException extends UnauthorizedException {
  public WrongCredentialsException(String message) {
    super(message);
  }
}
