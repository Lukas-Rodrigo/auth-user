package com.lucastexeira.authuser.adapters.in.controllers.user.mapper;

import com.lucastexeira.authuser.common.dto.MetaPaginationResponse;
import com.lucastexeira.authuser.common.dto.PaginationResponse;
import com.lucastexeira.authuser.adapters.in.controllers.user.dto.UserResponse;
import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;

import java.util.List;

public class UserPaginationMapper {

  public static PaginationResponse<UserResponse> toResponse(
      PageGenericResult<User> page
  ) {

    List<UserResponse> data = page.items()
        .stream()
        .map(user -> new UserResponse(
            user.getId(),
            user.getName(),
            user.getEmail()
        ))
        .toList();

    MetaPaginationResponse meta = new MetaPaginationResponse(
        page.page(),
        page.size(),
        page.totalItems(),
        page.totalPages(),
        page.hasNext(),
        page.hasPrevious()
    );

    return new PaginationResponse<>(data, meta);
  }
}