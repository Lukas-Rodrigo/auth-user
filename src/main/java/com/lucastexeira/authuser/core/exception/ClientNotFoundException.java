package com.lucastexeira.authuser.core.exception;

import java.util.UUID;

public class ClientNotFoundException extends NotFoundException {
  public ClientNotFoundException(UUID clientId) {
    super(
        "Client with ID" + clientId + " not found."
    );
  }
}
