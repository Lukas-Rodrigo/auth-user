package com.lucastexeira.authuser.core.port.in.client;

import com.lucastexeira.authuser.core.domain.Client;

import java.util.UUID;

public interface FindClientByIdOutputPort {

  Client execute(UUID clientId);
}
