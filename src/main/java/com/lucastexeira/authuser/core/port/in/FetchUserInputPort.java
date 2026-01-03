package com.lucastexeira.authuser.core.port.in;

import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.usecase.query.FetchQuery;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;

public interface FetchUserInputPort {
  public PageGenericResult<User> execute(FetchQuery query);
}
