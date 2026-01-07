package com.lucastexeira.authuser.core.port.out.user;

import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;

import java.time.LocalDate;

public interface FetchUsersOutputPort {

  PageGenericResult<User> execute(
      int page,
      int size,
      LocalDate startDate,
      LocalDate endDate
  );
}
