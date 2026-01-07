package com.lucastexeira.authuser.adapters.in.controllers.businessservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateBusinessServiceRequest(
    @NotBlank(message = "Name is required")
    String name,
    @NotNull(message = "price is required")
    @Min(value = 1, message = "price must be greater than or equal to 1")
    Integer price,
    @NotNull(message = "duration is required")
    @Min(value = 1, message = "duration must be greater than or equal to 1")
    Integer duration
) {


}
