package com.lucastexeira.authuser.adapters.out.businessservice;

import com.lucastexeira.authuser.adapters.out.persistence.entity.businesservice.BusinessServiceEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.businesservice.mapper.BusinessServicePersistenceMapper;
import com.lucastexeira.authuser.adapters.out.persistence.repository.BusinessServiceRepository;
import com.lucastexeira.authuser.core.domain.businessservices.BusinessService;
import com.lucastexeira.authuser.core.port.out.businessservices.FetchBusinessServicesOutputPort;
import com.lucastexeira.authuser.core.usecase.query.FetchQuery;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FetchBusinessServiceAdapter implements
    FetchBusinessServicesOutputPort {

  private final BusinessServiceRepository businessServiceRepository;

  public FetchBusinessServiceAdapter(BusinessServiceRepository businessServiceRepository) {
    this.businessServiceRepository = businessServiceRepository;
  }

  @Override
  public PageGenericResult<BusinessService> execute(
      UUID userId,
      FetchQuery fetchQuery
  ) {

    Pageable pageable = PageRequest.of(fetchQuery.page(), fetchQuery.size());

    Page<BusinessServiceEntity> result = businessServiceRepository.findAllWithFilter(
        userId,
        fetchQuery.startDate(),
        fetchQuery.endDate(),
        pageable
    );

    return new PageGenericResult<>(
        result.getContent().stream().map(
            BusinessServicePersistenceMapper.INSTANCE::toDomain
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
