package com.lucastexeira.authuser.core.port.in.client;

import java.util.UUID;

public interface DeleteClientByIdInputPort {

  void execute(UUID userId, UUID clientId);
}
