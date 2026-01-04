package com.lucastexeira.authuser.core.port.out.user;

import com.lucastexeira.authuser.core.domain.User;

public interface FindUserByEmailOutputPort {

  User execute(String email);
}
