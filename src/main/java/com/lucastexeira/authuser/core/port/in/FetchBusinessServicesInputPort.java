package com.lucastexeira.authuser.core.port.in;

import com.lucastexeira.authuser.core.domain.businessservices.BusinessService;
import com.lucastexeira.authuser.core.usecase.query.FetchQuery;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;

import java.util.UUID;

public interface FetchBusinessServicesInputPort {

  PageGenericResult<BusinessService> execute(UUID userId, FetchQuery fetchQuery);
}
