package com.lucastexeira.authuser.core.port.out.businessservices;

import java.util.UUID;

public interface DeleteBusinessServiceByIdOutputPort {

  void execute(UUID businessServiceId);
}
