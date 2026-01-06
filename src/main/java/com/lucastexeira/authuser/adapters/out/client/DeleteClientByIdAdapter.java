package com.lucastexeira.authuser.adapters.out.client;

import com.lucastexeira.authuser.adapters.out.persistence.repository.ClientRepository;
import com.lucastexeira.authuser.core.port.out.client.DeleteClientByIdOutputPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteClientByIdAdapter implements DeleteClientByIdOutputPort {

  private final ClientRepository clientRepository;


  public DeleteClientByIdAdapter(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public void execute(UUID clientId) {
    clientRepository.deleteById(clientId);
  }
}
