package com.lucastexeira.authuser.adapters.in.controllers.businessservice.mapper;

import com.lucastexeira.authuser.adapters.in.controllers.businessservice.dto.response.BusinessServiceResponse;
import com.lucastexeira.authuser.common.dto.MetaPaginationResponse;
import com.lucastexeira.authuser.common.dto.PaginationResponse;
import com.lucastexeira.authuser.core.domain.businessservices.BusinessService;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = BusinessServiceHTTPMapper.class)
public interface BusinessServicePaginationMapper {


  @Mapping(source = "items", target = "data")
  @Mapping(target = "meta", expression = "java(toMeta(page))")
  PaginationResponse<BusinessServiceResponse> toPaginationResponse(PageGenericResult<BusinessService> page);


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
