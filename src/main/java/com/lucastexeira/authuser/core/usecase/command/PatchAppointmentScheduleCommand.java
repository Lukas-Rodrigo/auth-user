package com.lucastexeira.authuser.core.usecase.command;

import java.time.LocalDateTime;
import java.util.UUID;

public record PatchAppointmentScheduleCommand(
    UUID appointmentId,
    LocalDateTime scheduleDateTime
) {
}
