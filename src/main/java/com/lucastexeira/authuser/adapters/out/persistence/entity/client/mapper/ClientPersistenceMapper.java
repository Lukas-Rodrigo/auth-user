package com.lucastexeira.authuser.adapters.out.persistence.entity.client.mapper;

import com.lucastexeira.authuser.adapters.out.persistence.entity.client.ClientEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.user.UserEntity;
import com.lucastexeira.authuser.core.domain.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ClientPersistenceMapper {

  ClientPersistenceMapper INSTANCE =
      org.mapstruct.factory.Mappers.getMapper(ClientPersistenceMapper.class);

  @Mapping(source = "user.id", target = "userId")
  Client toDomain(ClientEntity client);


  @Mapping(target = "user", expression = "java(mapUser(client.getUserId()))")
  ClientEntity toEntity(Client client);

  default UserEntity mapUser(UUID userId) {
    if (userId == null) {
      return null;
    }
    UserEntity user = new UserEntity();
    user.setId(userId);
    return user;
  }
}
