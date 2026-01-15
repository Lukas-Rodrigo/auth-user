package com.lucastexeira.authuser.adapters.in.controllers.client.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PatchClientRequest(
    @Size(min = 3, message = "Name must be at least 3 characters long")
    String name,
    @Size(min = 8, message = "Phone number must be at least 8 characters long")
    String phoneNumber,

    String profileUrl,
    @Size(min = 3, message = "Observations must be at least 3 characters long")
    String observations
) {
}
