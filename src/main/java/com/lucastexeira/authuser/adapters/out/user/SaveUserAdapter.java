package com.lucastexeira.authuser.adapters.out.user;

import com.lucastexeira.authuser.adapters.out.persistence.entity.user.mapper.UserPersistenceMapper;
import com.lucastexeira.authuser.adapters.out.persistence.repository.UserRepository;
import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.port.out.user.SaveUserOutputPort;
import org.springframework.stereotype.Component;

@Component
public class SaveUserAdapter implements SaveUserOutputPort {

  private final UserRepository userRepository;


  public SaveUserAdapter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User execute(User user) {
    var entity = UserPersistenceMapper.INSTANCE.toEntity(user);
    var savedEntity = userRepository.save(entity);
    return UserPersistenceMapper.INSTANCE.toDomain(savedEntity);

  }
}
