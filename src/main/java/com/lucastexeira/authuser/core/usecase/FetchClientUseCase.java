package com.lucastexeira.authuser.core.usecase;

import com.lucastexeira.authuser.core.domain.Client;
import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.exception.UserNotFoundException;
import com.lucastexeira.authuser.core.port.in.client.FetchClientInputPort;
import com.lucastexeira.authuser.core.port.out.client.FetchClientOutputPort;
import com.lucastexeira.authuser.core.port.out.user.FindUserByIdOutputPort;
import com.lucastexeira.authuser.core.usecase.query.FetchQuery;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;

import java.util.UUID;

public class FetchClientUseCase implements FetchClientInputPort {

  private final FindUserByIdOutputPort findUserByIdOutputPort;

  private final FetchClientOutputPort fetchClientOutputPort;

  public FetchClientUseCase(
      FindUserByIdOutputPort findUserByIdOutputPort,
      FetchClientOutputPort fetchClientOutputPort
  ) {
    this.findUserByIdOutputPort = findUserByIdOutputPort;
    this.fetchClientOutputPort = fetchClientOutputPort;
  }

  @Override
  public PageGenericResult<Client> execute(UUID userId, FetchQuery fetchQuery) {

    User isValidUser = findUserByIdOutputPort.execute(userId);

    if (isValidUser == null) {
      throw new UserNotFoundException(userId);
    }

    return this.fetchClientOutputPort.execute(userId, fetchQuery);


  }
}
