package com.lucastexeira.authuser.adapters.out.user;


import com.lucastexeira.authuser.adapters.out.persistence.entity.user.UserEntity;
import com.lucastexeira.authuser.adapters.out.persistence.repository.UserRepository;
import com.lucastexeira.authuser.core.port.out.FindUserByEmailOutputPort;
import com.lucastexeira.authuser.domain.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindUserByEmailAdapter implements FindUserByEmailOutputPort {

  private final UserRepository userRepository;

  public FindUserByEmailAdapter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Optional<User> find(String email) {
   var userEntityOptional = userRepository.findByEmail(email);
   return userEntityOptional.map(this::mapperToDomain);
  }

  private UserEntity mapperToEntity (User user){
    return new UserEntity(
        user.getId(),
        user.getName(),
        user.getPassword(),
        user.getEmail(),
        user.getCreatedAt()
    );
  }


  private User mapperToDomain(UserEntity userEntity) {
    return new User(
        userEntity.getId(),
        userEntity.getName(),
        userEntity.getPassword(),
        userEntity.getEmail(),
        userEntity.getCreatedAt()
    );
  }
}

