package com.lucastexeira.authuser.core.port.out.businessservices;

import com.lucastexeira.authuser.core.domain.businessservices.BusinessService;

import java.util.UUID;

public interface FindBusinessServiceByIdOutputPort {

  BusinessService execute(UUID businessServiceId);
}
