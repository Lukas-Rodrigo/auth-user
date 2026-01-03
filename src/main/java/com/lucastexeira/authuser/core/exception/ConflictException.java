package com.lucastexeira.authuser.core.exception;

public abstract class ConflictException extends RuntimeException {
  public ConflictException(String message) {
    super(message);
  }
}
