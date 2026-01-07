package com.lucastexeira.authuser.adapters.in.controllers.appointment.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponse(
    UUID id,
    LocalDateTime scheduledAt,
    String status,
    LocalDate createdAt
) {
}
