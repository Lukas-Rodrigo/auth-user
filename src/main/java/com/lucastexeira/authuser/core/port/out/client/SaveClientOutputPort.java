package com.lucastexeira.authuser.core.port.out.client;

import com.lucastexeira.authuser.core.domain.Client;

public interface SaveClientOutputPort {

  Client execute(Client client);
}
