package com.lucastexeira.authuser.core.exception;

public class WrongCredentialsException extends RuntimeException {
  public WrongCredentialsException(String message) {
    super(message);
  }
}
