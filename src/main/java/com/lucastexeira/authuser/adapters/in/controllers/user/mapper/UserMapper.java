package com.lucastexeira.authuser.adapters.in.controllers.user.mapper;

import com.lucastexeira.authuser.adapters.in.controllers.user.dto.UserRequest;
import com.lucastexeira.authuser.adapters.in.controllers.user.dto.UserResponse;
import com.lucastexeira.authuser.core.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  User toDomain(UserRequest userRequest);

  UserResponse toResponseDTO(User user);

}
