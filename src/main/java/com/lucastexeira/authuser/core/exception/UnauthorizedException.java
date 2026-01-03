package com.lucastexeira.authuser.core.exception;

public abstract  class UnauthorizedException extends RuntimeException {
  protected UnauthorizedException(String message) {
    super(message);
  }
}
