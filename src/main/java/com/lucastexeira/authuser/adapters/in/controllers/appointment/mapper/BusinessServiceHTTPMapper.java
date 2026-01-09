package com.lucastexeira.authuser.adapters.in.controllers.appointment.mapper;

import com.lucastexeira.authuser.adapters.in.controllers.businessservice.dto.response.BusinessServiceResponse;
import com.lucastexeira.authuser.common.dto.MetaPaginationResponse;
import com.lucastexeira.authuser.common.dto.PaginationResponse;
import com.lucastexeira.authuser.core.domain.businessservices.BusinessService;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;

public class BusinessServiceHTTPMapper {

  public static PaginationResponse<BusinessServiceResponse> toHTTP(PageGenericResult<BusinessService> services) {

    var list = services.items().stream().map(
        service -> new BusinessServiceResponse(
            service.getId(),
            service.getName(),
            service.getPrice().getAmount(),
            service.getDuration().toMinutes()
        )).toList();

    var meta = new MetaPaginationResponse(
        services.page(),
        services.size(),
        services.totalItems(),
        services.totalPages(),
        services.hasNext(),
        services.hasPrevious()
    );


    return new PaginationResponse<>(list, meta);

  }


}
