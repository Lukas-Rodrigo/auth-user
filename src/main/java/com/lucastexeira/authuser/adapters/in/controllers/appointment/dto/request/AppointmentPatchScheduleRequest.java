package com.lucastexeira.authuser.adapters.in.controllers.appointment.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentPatchScheduleRequest(

    @Future(message = "Schedule date must be in the future")
    @NotNull(message = "Schedule is required")
    LocalDateTime schedule
) {
}
