package com.lucastexeira.authuser.adapters.out.client;

import com.lucastexeira.authuser.adapters.out.persistence.repository.ClientRepository;
import com.lucastexeira.authuser.core.port.out.client.ExistsByIdAndUserIdOutputPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ExistsByIdAndUserIdAdapter implements
    ExistsByIdAndUserIdOutputPort {

  private final ClientRepository clientRepository;


  public ExistsByIdAndUserIdAdapter(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public boolean execute(
      UUID clientId,
      UUID userId
  ) {
    return clientRepository.existsByIdAndUserId(clientId, userId);
  }
}
