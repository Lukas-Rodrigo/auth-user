package com.lucastexeira.authuser.adapters.out.user;

import com.lucastexeira.authuser.adapters.out.persistence.entity.user.mapper.UserPersistenceMapper;
import com.lucastexeira.authuser.adapters.out.persistence.repository.UserRepository;
import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.port.out.user.FindUserByIdOutputPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FindUserByIdAdapter implements FindUserByIdOutputPort {

  private final UserRepository userRepository;

  public FindUserByIdAdapter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User execute(UUID userId) {
    var userEntity = userRepository.findById(userId).isPresent() ?
                     userRepository.findById(userId).get() : null;
    return UserPersistenceMapper.INSTANCE.toDomain(userEntity);
  }
}
