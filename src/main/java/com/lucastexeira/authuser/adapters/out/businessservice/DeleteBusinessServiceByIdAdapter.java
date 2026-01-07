package com.lucastexeira.authuser.adapters.out.businessservice;

import com.lucastexeira.authuser.adapters.out.persistence.repository.BusinessServiceRepository;
import com.lucastexeira.authuser.core.port.out.businessservices.DeleteBusinessServiceByIdOutputPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteBusinessServiceByIdAdapter implements
    DeleteBusinessServiceByIdOutputPort {

  private final BusinessServiceRepository businessServiceRepository;


  public DeleteBusinessServiceByIdAdapter(BusinessServiceRepository businessServiceRepository) {
    this.businessServiceRepository = businessServiceRepository;
  }

  @Override
  public void execute(UUID businessServiceId) {
    businessServiceRepository.deleteById(businessServiceId);
  }
}
