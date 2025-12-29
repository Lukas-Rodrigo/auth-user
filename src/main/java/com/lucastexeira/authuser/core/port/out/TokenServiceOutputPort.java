package com.lucastexeira.authuser.core.port.out;

import com.lucastexeira.authuser.core.domain.User;

public interface TokenServiceOutputPort {

  String generateToken(User user);
}
