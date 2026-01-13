package com.lucastexeira.authuser.adapters.in.controllers.client.dto.response;

import java.util.UUID;

public record ClientResponse(
    UUID id,
    String name,
    String phoneNumber,
    String profileUrl,
    String observations,
    String createdAt
) {
}
