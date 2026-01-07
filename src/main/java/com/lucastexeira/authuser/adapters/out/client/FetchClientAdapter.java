package com.lucastexeira.authuser.adapters.out.client;

import com.lucastexeira.authuser.adapters.out.persistence.entity.client.ClientEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.client.mapper.ClientPersistenceMapper;
import com.lucastexeira.authuser.adapters.out.persistence.repository.ClientRepository;
import com.lucastexeira.authuser.core.domain.Client;
import com.lucastexeira.authuser.core.port.out.client.FetchClientOutputPort;
import com.lucastexeira.authuser.core.usecase.query.FetchQuery;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FetchClientAdapter implements FetchClientOutputPort {

  private final ClientRepository clientRepository;

  public FetchClientAdapter(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public PageGenericResult<Client> execute(
      UUID userId,
      FetchQuery fetchQuery
  ) {

    Pageable pageable = PageRequest.of(fetchQuery.page(), fetchQuery.size());

    Page<ClientEntity> result = clientRepository.findAllWithFilter(
        userId,
        fetchQuery.startDate(),
        fetchQuery.endDate(),
        pageable
    );

    return new PageGenericResult<>(
        result.getContent().stream().map(
            ClientPersistenceMapper.INSTANCE::toDomain
        ).toList(),
        result.getNumber(),
        result.getSize(),
        result.getTotalElements(),
        result.getTotalPages(),
        result.hasNext(),
        result.hasPrevious()
    );

  }
}
