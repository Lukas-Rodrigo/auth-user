package com.lucastexeira.authuser.core.usecase.command;

import java.util.UUID;

public record PatchClientCommand(
    UUID id,
    String name,
    String phoneNumber,
    String observations,
    String profileUrl
) {
}
