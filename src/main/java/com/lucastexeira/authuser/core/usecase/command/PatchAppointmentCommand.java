package com.lucastexeira.authuser.core.usecase.command;

import com.lucastexeira.authuser.common.enums.AppointmentStatus;

import java.util.UUID;

public record PatchAppointmentCommand(
    UUID userId,
    UUID appointmentId,
    AppointmentStatus status
) {
}
