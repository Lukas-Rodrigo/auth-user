package com.lucastexeira.authuser.core.port.out.client;

import com.lucastexeira.authuser.core.domain.Client;

import java.util.UUID;

public interface FindClientByPhoneNumberAndNameAndUserIdOutputPort {
  Client execute( UUID userId,
                  String phoneNumber,
                  String name);
}
