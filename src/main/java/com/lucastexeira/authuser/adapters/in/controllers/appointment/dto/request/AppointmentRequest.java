package com.lucastexeira.authuser.adapters.in.controllers.appointment.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record AppointmentRequest(

    UUID clientId,
    @Future(message = "The scheduled date must be in the future")
    @NotBlank(message = "The scheduled date cannot be blank")
    LocalDateTime scheduledAt,
    List<Services> services

) {

  public record Services(
      @NotBlank(message = "The business service ID cannot be blank")
      UUID businessServiceId,

      @Positive(message = "The price must be a positive value")
      BigDecimal discount
  ) {
  }


}


