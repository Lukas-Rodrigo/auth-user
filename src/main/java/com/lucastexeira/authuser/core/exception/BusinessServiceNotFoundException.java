package com.lucastexeira.authuser.core.exception;

import java.util.UUID;

public class BusinessServiceNotFoundException extends NotFoundException {
  public BusinessServiceNotFoundException(UUID businessServiceId) {
    super("Business service with ID " + businessServiceId + " not found.");
  }

  public BusinessServiceNotFoundException() {
    super("Business service not found.");
  }
}
