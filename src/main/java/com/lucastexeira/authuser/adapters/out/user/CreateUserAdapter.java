package com.lucastexeira.authuser.adapters.out.user;

import com.lucastexeira.authuser.adapters.out.persistence.entity.user.UserEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.user.mapper.UserPersistenceMapper;
import com.lucastexeira.authuser.adapters.out.persistence.repository.UserRepository;
import com.lucastexeira.authuser.core.port.out.CreateUserOutputPort;
import com.lucastexeira.authuser.domain.User;
import org.springframework.stereotype.Component;

@Component
public class CreateUserAdapter implements CreateUserOutputPort {

  private final UserRepository userRepository;
  private final UserPersistenceMapper userPersistenceMapper;


  public CreateUserAdapter(UserRepository userRepository, UserPersistenceMapper userPersistenceMapper) {
    this.userRepository = userRepository;
    this.userPersistenceMapper = userPersistenceMapper;
  }

  @Override
  public User createUser(User user) {
    var entity = userPersistenceMapper.toEntity(user);
    var savedEntity = userRepository.save(entity);
     return userPersistenceMapper.toDomain(savedEntity);

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
