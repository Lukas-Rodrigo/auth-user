package com.lucastexeira.authuser.core.port.out;

import com.lucastexeira.authuser.domain.User;

public interface CreateUserOutputPort {

  public User createUser(User user);
}
