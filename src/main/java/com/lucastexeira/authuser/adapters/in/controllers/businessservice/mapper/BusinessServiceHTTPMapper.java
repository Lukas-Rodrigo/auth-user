package com.lucastexeira.authuser.adapters.in.controllers.businessservice.mapper;

import com.lucastexeira.authuser.adapters.in.controllers.businessservice.dto.response.BusinessServiceResponse;
import com.lucastexeira.authuser.core.domain.businessservices.BusinessService;
import com.lucastexeira.authuser.core.domain.valueobject.Money;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.time.Duration;

@Mapper(componentModel = "spring")
public interface BusinessServiceHTTPMapper {

  BusinessServiceResponse toResponse(BusinessService service);

  default BigDecimal map(Money value) {
    return value != null ? value.getAmount() : null;
  }

  default Long map(Duration value) {
    return value != null ? value.toMinutes() : null;
  }
}
