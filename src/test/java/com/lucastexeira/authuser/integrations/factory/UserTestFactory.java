package com.lucastexeira.authuser.integrations.factory;

import com.lucastexeira.authuser.adapters.out.persistence.entity.user.UserEntity;
import com.lucastexeira.authuser.adapters.out.persistence.repository.UserRepository;
import com.lucastexeira.authuser.common.enums.Role;
import com.lucastexeira.authuser.core.exception.UserAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public class UserTestFactory {

  public static UserEntity createUser(
      UserRepository userRepository,
      PasswordEncoder encoder,
      String email
  ) {
    UserEntity userEntity = new UserEntity();
    userEntity.setName("User" + email);
    userEntity.setEmail(email);
    userEntity.setRole(Role.USER);
    userEntity.setPassword(encoder.encode("password123"));
    return userRepository.save(userEntity);
  }
}
