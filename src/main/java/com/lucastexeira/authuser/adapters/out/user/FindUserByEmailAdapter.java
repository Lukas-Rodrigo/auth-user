package com.lucastexeira.authuser.adapters.out.user;

import com.lucastexeira.authuser.adapters.out.persistence.entity.user.UserEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.user.mapper.UserPersistenceMapper;
import com.lucastexeira.authuser.adapters.out.persistence.repository.UserRepository;
import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.port.out.user.FindUserByEmailOutputPort;
import org.springframework.stereotype.Component;

@Component
public class FindUserByEmailAdapter implements FindUserByEmailOutputPort {

  private final UserRepository userRepository;

  public FindUserByEmailAdapter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User execute(String email) {
    UserEntity userFound = userRepository.findByEmail(email).orElse(null);
    return UserPersistenceMapper.INSTANCE.toDomain(userFound);
  }
}
