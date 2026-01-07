package com.lucastexeira.authuser.core.usecase.command;

import java.util.UUID;

public record CreateBusinessServiceCommand(
    UUID userId,
    String name,
    Integer price,
    Integer duration
) {
}
