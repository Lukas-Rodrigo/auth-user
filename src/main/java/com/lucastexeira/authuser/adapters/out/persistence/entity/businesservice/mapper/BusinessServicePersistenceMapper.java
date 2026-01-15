package com.lucastexeira.authuser.adapters.out.persistence.entity.businesservice.mapper;

import com.lucastexeira.authuser.adapters.out.persistence.entity.businesservice.BusinessServiceEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.user.UserEntity;
import com.lucastexeira.authuser.core.domain.businessservices.BusinessService;
import com.lucastexeira.authuser.core.domain.valueobject.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BusinessServicePersistenceMapper {

  BusinessServicePersistenceMapper INSTANCE = Mappers.getMapper(
      BusinessServicePersistenceMapper.class);

  @Mapping(source = "user.id", target = "userId")
  BusinessService toDomain(BusinessServiceEntity businessServiceEntity);

  @Mapping(target = "user", expression = "java(mapUser(businessService.getUserId()))")
  BusinessServiceEntity toEntity(BusinessService businessService);

  default UserEntity mapUser(UUID userId) {
    if (userId == null) {
      return null;
    }
    UserEntity user = new UserEntity();
    user.setId(userId);
    return user;
  }

  default Money map(BigDecimal value) {
    return value != null ? new Money(value) : null;
  }

  default BigDecimal map(Money value) {
    return value != null ? value.getAmount() : null;
  }

  default Duration map(Long value) {
    return value != null ? Duration.ofMinutes(value) : null;
  }

  default Long map(Duration value) {
    return value != null ? value.toMinutes() : null;
  }
}
