package com.lucastexeira.authuser.adapters.in.controllers.client.mapper;

import com.lucastexeira.authuser.adapters.in.controllers.client.dto.response.ClientResponse;
import com.lucastexeira.authuser.common.dto.MetaPaginationResponse;
import com.lucastexeira.authuser.common.dto.PaginationResponse;
import com.lucastexeira.authuser.core.domain.Client;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ClientHTTPMapper.class)
public interface ClientPaginationMapper {

  @Mapping(source = "items", target = "data")
  @Mapping(target = "meta", expression = "java(toMeta(page))")
  PaginationResponse<ClientResponse> toPaginationResponse(PageGenericResult<Client> page);


  default MetaPaginationResponse toMeta(PageGenericResult<?> page) {
    return new MetaPaginationResponse(
        page.page(),
        page.size(),
        page.totalItems(),
        page.totalPages(),
        page.hasNext(),
        page.hasPrevious()
    );
  }
}
