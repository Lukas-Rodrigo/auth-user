package com.lucastexeira.authuser.adapters.in.controllers.client.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateClientRequest(
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    String name,
    @NotBlank(message = "Phone number cannot be blank")
    @Size(min = 8, message = "Phone number must be at least 8 characters long")
    String phoneNumber,

    String profileUrl,
    String observations
) {
}
