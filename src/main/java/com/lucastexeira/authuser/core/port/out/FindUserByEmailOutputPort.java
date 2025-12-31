package com.lucastexeira.authuser.core.port.out;

import com.lucastexeira.authuser.domain.User;

import java.util.Optional;

public interface FindUserByEmailOutputPort {

  Optional<User> find(String email);


}
