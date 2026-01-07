package com.lucastexeira.authuser.core.usecase.command;

import java.util.UUID;

public record ServiceItemCommand(
    UUID businessServiceId,
    Integer discount
) {
}
