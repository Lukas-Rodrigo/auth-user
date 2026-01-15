package com.lucastexeira.authuser.adapters.in.controllers.client.mapper;

import com.lucastexeira.authuser.adapters.in.controllers.client.dto.response.ClientResponse;
import com.lucastexeira.authuser.core.domain.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientHTTPMapper {

  ClientResponse toResponse(Client client);
}
