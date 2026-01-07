package com.lucastexeira.authuser.adapters.in.controllers.businessservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record PatchBusinessServiceRequest(
    @Size(min = 2, message = "Name must have at least 2 characters")
    String name,

    @Size(min = 2, message = "Name must have at least 2 characters")
    String description,

    @Min(value = 1, message = "Price must be at least 1")
    Integer price,

    @Min(value = 1, message = "Duration must be at least 1")
    Integer duration
) {
}
