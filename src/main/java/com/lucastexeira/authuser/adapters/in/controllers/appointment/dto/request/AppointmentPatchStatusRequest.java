package com.lucastexeira.authuser.adapters.in.controllers.appointment.dto.request;

import com.lucastexeira.authuser.infra.annotations.ValidAppointmentStatus;
import jakarta.validation.constraints.NotBlank;


public record AppointmentPatchStatusRequest(

    @NotBlank(message =
        "Status is required: PENDING, CONFIRMED, CANCELLED, COMPLETED")
    @ValidAppointmentStatus
    String status

) {}