package com.lucastexeira.authuser.core.usecase.query;

import java.time.LocalDate;

public record FetchQuery(
    int page,
    int size,
    LocalDate startDate,
    LocalDate endDate
) {

  public FetchQuery {
    if (size <= 0) {
      size = 15;
    }
    if (page < 0) {
      page = 0;
    }
  }
}
