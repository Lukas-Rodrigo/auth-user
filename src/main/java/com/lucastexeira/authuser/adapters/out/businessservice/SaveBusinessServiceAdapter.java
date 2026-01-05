package com.lucastexeira.authuser.adapters.out.businessservice;

import com.lucastexeira.authuser.adapters.out.persistence.entity.businesservice.mapper.BusinessServicePersistenceMapper;
import com.lucastexeira.authuser.adapters.out.persistence.repository.BusinessServiceRepository;
import com.lucastexeira.authuser.core.domain.businessservices.BusinessService;
import com.lucastexeira.authuser.core.port.out.businessservices.SaveBusinessServiceOutputPort;
import org.springframework.stereotype.Component;

@Component
public class SaveBusinessServiceAdapter implements
    SaveBusinessServiceOutputPort {

  private final BusinessServiceRepository businessServiceRepository;


  public SaveBusinessServiceAdapter(BusinessServiceRepository businessServiceRepository) {
    this.businessServiceRepository = businessServiceRepository;
  }

  @Override
  public BusinessService execute(BusinessService businessService) {
    var businessServiceEntity =
        BusinessServicePersistenceMapper.INSTANCE.toEntity(businessService);
    var savedEntity = businessServiceRepository.save(businessServiceEntity);
    return BusinessServicePersistenceMapper.INSTANCE.toDomain(savedEntity);
  }
}
