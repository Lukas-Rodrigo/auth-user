package com.lucastexeira.authuser.core.port.in;

import com.lucastexeira.authuser.domain.User;

public interface CreateUserInputPort {

  public User createUser(User user);
}
