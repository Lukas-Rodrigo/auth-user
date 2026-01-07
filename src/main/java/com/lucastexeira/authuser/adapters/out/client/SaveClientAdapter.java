package com.lucastexeira.authuser.adapters.out.client;

import com.lucastexeira.authuser.adapters.out.persistence.entity.client.mapper.ClientPersistenceMapper;
import com.lucastexeira.authuser.adapters.out.persistence.repository.ClientRepository;
import com.lucastexeira.authuser.core.domain.Client;
import com.lucastexeira.authuser.core.port.out.client.SaveClientOutputPort;
import org.springframework.stereotype.Component;

@Component
public class SaveClientAdapter implements SaveClientOutputPort {

  private final ClientRepository clientRepository;

  public SaveClientAdapter(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public Client execute(Client client) {
    var entity = ClientPersistenceMapper.INSTANCE.toEntity(client);
    var savedEntity = clientRepository.save(entity);
    return ClientPersistenceMapper.INSTANCE.toDomain(savedEntity);
  }
}
