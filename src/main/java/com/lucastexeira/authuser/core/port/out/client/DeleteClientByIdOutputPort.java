package com.lucastexeira.authuser.core.port.out.client;

import java.util.UUID;

public interface DeleteClientByIdOutputPort {

  void execute(UUID clientId);
}
