package com.lucastexeira.authuser.core.port.in.client;

import com.lucastexeira.authuser.core.domain.Client;
import com.lucastexeira.authuser.core.usecase.command.PatchClientCommand;

import java.util.UUID;

public interface PatchClientInputPort {
  Client execute(UUID userId, PatchClientCommand command);
}
