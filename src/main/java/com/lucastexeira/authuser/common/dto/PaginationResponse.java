package com.lucastexeira.authuser.common.dto;

import java.util.List;

public record PaginationResponse<T>(
    List<T> data,
    MetaPaginationResponse meta
) {
}
