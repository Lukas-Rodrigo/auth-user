package com.lucastexeira.authuser.core.port.out.user;

import com.lucastexeira.authuser.core.domain.User;

import java.util.UUID;

public interface FindUserByIdOutputPort {

  User execute(UUID userId);
}
