package com.lucastexeira.authuser.core.exception;

public class InvalidScheduleDateException extends DomainException {
  public InvalidScheduleDateException() {
    super("Scheduled date must be in the future");
  }
}
