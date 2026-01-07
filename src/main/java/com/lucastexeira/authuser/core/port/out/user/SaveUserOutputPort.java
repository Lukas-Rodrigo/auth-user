package com.lucastexeira.authuser.core.port.out.user;

import com.lucastexeira.authuser.core.domain.User;

public interface SaveUserOutputPort {

  public User execute(User user);
}
