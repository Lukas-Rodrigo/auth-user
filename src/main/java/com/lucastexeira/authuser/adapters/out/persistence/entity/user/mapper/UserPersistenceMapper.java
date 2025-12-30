package com.lucastexeira.authuser.adapters.out.persistence.entity.user.mapper;

import com.lucastexeira.authuser.adapters.out.persistence.entity.user.UserEntity;
import com.lucastexeira.authuser.core.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {

  UserPersistenceMapper INSTANCE =
      Mappers.getMapper(UserPersistenceMapper.class);

  User toDomain(UserEntity userEntity);

  @Mapping(target = "id", ignore = true)
  UserEntity toEntity(User user);
}
