package com.lucastexeira.authuser.adapters.out.client;

import com.lucastexeira.authuser.adapters.out.persistence.entity.client.ClientEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.client.mapper.ClientPersistenceMapper;
import com.lucastexeira.authuser.adapters.out.persistence.repository.ClientRepository;
import com.lucastexeira.authuser.core.domain.Client;
import com.lucastexeira.authuser.core.port.in.client.FindClientByIdOutputPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FindClientByIdAdapter implements FindClientByIdOutputPort {

  private final ClientRepository clientRepository;

  public FindClientByIdAdapter(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public Client execute(UUID clientId) {
    ClientEntity client = clientRepository.findById(clientId).isPresent() ?
                          clientRepository.findById(clientId).get() : null;
    return ClientPersistenceMapper.INSTANCE.toDomain(client);
  }
}
