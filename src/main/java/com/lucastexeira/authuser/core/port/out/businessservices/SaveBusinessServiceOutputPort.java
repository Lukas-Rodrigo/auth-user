package com.lucastexeira.authuser.core.port.out.businessservices;

import com.lucastexeira.authuser.core.domain.businessservices.BusinessService;

public interface SaveBusinessServiceOutputPort {

  BusinessService execute(BusinessService businessService);
}
