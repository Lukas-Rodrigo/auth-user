package com.lucastexeira.authuser.core.usecase.result;

import java.util.List;

public record PageGenericResult<T>(
    List<T> items,
    int page,
    int size,
    long totalItems,
    int totalPages,
    boolean hasNext,
    boolean hasPrevious

) {
}
