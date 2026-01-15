package com.lucastexeira.authuser.core.port.in.client;

import com.lucastexeira.authuser.core.domain.Client;
import com.lucastexeira.authuser.core.usecase.command.CreateClientCommand;

import java.util.UUID;

public interface CreateClientInputPort {

  Client execute(UUID userId, CreateClientCommand command);
}
