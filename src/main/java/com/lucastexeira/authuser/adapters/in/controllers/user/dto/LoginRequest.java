package com.lucastexeira.authuser.adapters.in.controllers.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    String email,

    @Size(min = 6, message = "Password must be at least 6 characters long")
    @NotBlank(message = "Password cannot be blank")
    String password
) {
}
