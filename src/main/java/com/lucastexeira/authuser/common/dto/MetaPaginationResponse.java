package com.lucastexeira.authuser.common.dto;

public record MetaPaginationResponse(
    int page,
    int size,
    long totalItems,
    int totalPages,
    boolean hasNext,
    boolean hasPrevious
) {
}
