package com.lucastexeira.authuser.core.port.out.businessservices;

import com.lucastexeira.authuser.core.domain.businessservices.BusinessService;
import com.lucastexeira.authuser.core.usecase.query.FetchQuery;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;

import java.util.UUID;

public interface FetchBusinessServicesOutputPort {
  PageGenericResult<BusinessService> execute(UUID userId, FetchQuery fetchQuery);
}
