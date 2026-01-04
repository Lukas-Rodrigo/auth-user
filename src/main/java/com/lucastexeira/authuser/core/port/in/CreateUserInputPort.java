package com.lucastexeira.authuser.core.port.in;

import com.lucastexeira.authuser.core.domain.User;

public interface CreateUserInputPort {

  public User createUser(User user);
}
