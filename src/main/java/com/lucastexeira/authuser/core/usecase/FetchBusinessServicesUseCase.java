package com.lucastexeira.authuser.core.usecase;

import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.domain.businessservices.BusinessService;
import com.lucastexeira.authuser.core.exception.UserNotFoundException;
import com.lucastexeira.authuser.core.port.in.FetchBusinessServicesInputPort;
import com.lucastexeira.authuser.core.port.out.businessservices.FetchBusinessServicesOutputPort;
import com.lucastexeira.authuser.core.port.out.user.FindUserByIdOutputPort;
import com.lucastexeira.authuser.core.usecase.query.FetchQuery;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;

import java.util.UUID;

public class FetchBusinessServicesUseCase implements
    FetchBusinessServicesInputPort {

  private final FetchBusinessServicesOutputPort fetchBusinessServicesOutputPort;

  private final FindUserByIdOutputPort findUserByIdOutputPort;

  public FetchBusinessServicesUseCase(FetchBusinessServicesOutputPort fetchBusinessServicesOutputPort,
                                      FindUserByIdOutputPort findUserByIdOutputPort) {
    this.fetchBusinessServicesOutputPort = fetchBusinessServicesOutputPort;
    this.findUserByIdOutputPort = findUserByIdOutputPort;
  }

  @Override
  public PageGenericResult<BusinessService> execute(
      UUID userId,
      FetchQuery fetchQuery
  ) {

    User isValidUser = findUserByIdOutputPort.execute(userId);

    if (isValidUser == null) {
      throw new UserNotFoundException(userId);
    }
    return this.fetchBusinessServicesOutputPort.execute(userId, fetchQuery);
  }
}
