package com.lucastexeira.authuser.core.usecase.command;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CreateAppointmentCommand(
    UUID userId,
    UUID clientId,
    LocalDateTime scheduledAt,
    List<ServiceItemCommand> services
) {
}


