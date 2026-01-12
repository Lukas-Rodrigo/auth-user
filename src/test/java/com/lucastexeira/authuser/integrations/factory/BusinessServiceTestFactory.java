package com.lucastexeira.authuser.integrations.factory;


import com.lucastexeira.authuser.adapters.out.persistence.entity.businesservice.BusinessServiceEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.user.UserEntity;
import com.lucastexeira.authuser.adapters.out.persistence.repository.BusinessServiceRepository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

public class BusinessServiceTestFactory {


  public static BusinessServiceEntity createBusinessServiceEntity(
      BusinessServiceRepository businessServiceRepository,
      UserEntity user
  ) {
    BusinessServiceEntity service = new BusinessServiceEntity();
    service.setName("Haircut");
    service.setPrice(new BigDecimal("50.00"));
    service.setDuration(Duration.ofMinutes(60));
    service.setCreatedAt(LocalDate.now());
    service.setUser(user);
    return businessServiceRepository.save(service);
  }
}
