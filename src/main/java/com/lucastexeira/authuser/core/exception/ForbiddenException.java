package com.lucastexeira.authuser.core.exception;

public abstract  class ForbiddenException extends RuntimeException {
  protected ForbiddenException(String message) {
    super(message);
  }
}
