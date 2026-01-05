package com.lucastexeira.authuser.adapters.out.client;

import com.lucastexeira.authuser.adapters.out.persistence.entity.client.mapper.ClientPersistenceMapper;
import com.lucastexeira.authuser.adapters.out.persistence.repository.ClientRepository;
import com.lucastexeira.authuser.core.domain.Client;
import com.lucastexeira.authuser.core.port.out.client.FindClientByPhoneNumberAndNameAndUserIdOutputPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FindClientByPhoneNumberAndNameAndUserAdapter implements
    FindClientByPhoneNumberAndNameAndUserIdOutputPort {

  private final ClientRepository clientRepository;

  public FindClientByPhoneNumberAndNameAndUserAdapter(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public Client execute(
      UUID userId,
      String phoneNumber,
      String name
  ) {
    var clientFound =
        clientRepository.findClientEntitiesByPhoneNumberAndNameAndUserId(phoneNumber, name, userId);
    return ClientPersistenceMapper.INSTANCE.toDomain(clientFound);
  }
}
