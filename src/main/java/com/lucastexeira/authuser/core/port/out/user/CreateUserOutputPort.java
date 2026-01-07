package com.lucastexeira.authuser.core.port.out.user;

import com.lucastexeira.authuser.core.domain.User;

public interface CreateUserOutputPort {

  public User createUser(User user);
}
