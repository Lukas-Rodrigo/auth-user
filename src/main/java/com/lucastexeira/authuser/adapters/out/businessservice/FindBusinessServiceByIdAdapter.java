package com.lucastexeira.authuser.adapters.out.businessservice;

import com.lucastexeira.authuser.adapters.out.persistence.entity.businesservice.BusinessServiceEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.businesservice.mapper.BusinessServicePersistenceMapper;
import com.lucastexeira.authuser.adapters.out.persistence.repository.BusinessServiceRepository;
import com.lucastexeira.authuser.core.domain.businessservices.BusinessService;
import com.lucastexeira.authuser.core.port.out.businessservices.FindBusinessServiceByIdOutputPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FindBusinessServiceByIdAdapter implements
    FindBusinessServiceByIdOutputPort {

  private final BusinessServiceRepository businessServiceRepository;


  public FindBusinessServiceByIdAdapter(BusinessServiceRepository businessServiceRepository) {
    this.businessServiceRepository = businessServiceRepository;
  }


  @Override
  public BusinessService execute(UUID businessServiceId) {
    BusinessServiceEntity serviceEntity =
        businessServiceRepository.findById(businessServiceId).isPresent() ?
        businessServiceRepository.findById(businessServiceId).get() : null;
    return BusinessServicePersistenceMapper.INSTANCE.toDomain(serviceEntity);
  }
}
