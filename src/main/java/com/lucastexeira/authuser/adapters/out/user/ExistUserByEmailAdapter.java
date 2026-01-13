package com.lucastexeira.authuser.adapters.out.user;


import com.lucastexeira.authuser.adapters.out.persistence.repository.UserRepository;
import com.lucastexeira.authuser.core.port.out.user.ExistUserByEmailOutputPort;
import org.springframework.stereotype.Component;


@Component
public class ExistUserByEmailAdapter implements ExistUserByEmailOutputPort {

  private final UserRepository userRepository;

  public ExistUserByEmailAdapter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public boolean execute(String email) {
    return userRepository.existsByEmail(email);

  }

}

