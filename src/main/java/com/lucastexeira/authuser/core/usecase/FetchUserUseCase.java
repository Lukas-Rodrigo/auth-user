package com.lucastexeira.authuser.core.usecase;

import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.port.in.FetchUserInputPort;
import com.lucastexeira.authuser.core.port.out.user.FetchUsersOutputPort;
import com.lucastexeira.authuser.core.usecase.query.FetchQuery;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;


public class FetchUserUseCase implements FetchUserInputPort {

  private final FetchUsersOutputPort fetchUsersOutputPort;

  public FetchUserUseCase(FetchUsersOutputPort fetchUsersOutputPort) {
    this.fetchUsersOutputPort = fetchUsersOutputPort;
  }


  @Override
  public PageGenericResult<User> execute(FetchQuery query) {
    return fetchUsersOutputPort.execute(
        query.page(),
        query.size(),
        query.startDate(),
        query.endDate()
    );
  }

}
