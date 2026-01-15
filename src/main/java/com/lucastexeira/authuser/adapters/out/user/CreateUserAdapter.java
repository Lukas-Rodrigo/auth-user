package com.lucastexeira.authuser.adapters.out.user;

import com.lucastexeira.authuser.adapters.out.persistence.entity.user.mapper.UserPersistenceMapper;
import com.lucastexeira.authuser.adapters.out.persistence.repository.UserRepository;
import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.port.out.user.CreateUserOutputPort;
import org.springframework.stereotype.Component;

@Component
public class CreateUserAdapter implements CreateUserOutputPort {

  private final UserRepository userRepository;


  public CreateUserAdapter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User createUser(User user) {
    var entity = UserPersistenceMapper.INSTANCE.toEntity(user);
    var savedEntity = userRepository.save(entity);
    return UserPersistenceMapper.INSTANCE.toDomain(savedEntity);

  }
}
