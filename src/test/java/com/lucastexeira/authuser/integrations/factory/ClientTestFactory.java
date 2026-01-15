package com.lucastexeira.authuser.integrations.factory;

import com.lucastexeira.authuser.adapters.out.persistence.entity.client.ClientEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.user.UserEntity;
import com.lucastexeira.authuser.adapters.out.persistence.repository.ClientRepository;

import java.time.LocalDate;

public class ClientTestFactory {

  public static ClientEntity createClient(
      ClientRepository clientRepository,
      UserEntity user
  ) {

    ClientEntity client = new ClientEntity();
    client.setName("John Doe");
    client.setPhoneNumber("7581069197");
    client.setCreatedAt(LocalDate.now());
    client.setUser(user);
    return clientRepository.save(client);

  }
}
