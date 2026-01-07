package com.lucastexeira.authuser.core.usecase.command;

public record CreateClientCommand(
    String name,
    String phoneNumber,
    String profileUrl,
    String observations
) {
}
