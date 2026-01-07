package com.lucastexeira.authuser.adapters.in.controllers.appointment.mapper;

import com.lucastexeira.authuser.common.dto.MetaPaginationResponse;
import com.lucastexeira.authuser.common.dto.PaginationResponse;
import com.lucastexeira.authuser.core.usecase.result.AppointmentDetailsResult;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;

import java.util.List;

public class AppointmentMapper {

  public static PaginationResponse<AppointmentDetailsResult> toResponse(
      PageGenericResult<AppointmentDetailsResult> pageGenericResult
  ) {

    List<AppointmentDetailsResult> data = pageGenericResult.items().stream()
        .map(appointmentDetailsResult -> new AppointmentDetailsResult(
            appointmentDetailsResult.id(),
            appointmentDetailsResult.scheduledAt(),
            appointmentDetailsResult.status(),
            appointmentDetailsResult.client(),
            appointmentDetailsResult.services(),
            appointmentDetailsResult.totalPrice(),
            appointmentDetailsResult.createdAt()
        ))
        .toList();

    var meta = new MetaPaginationResponse(
        pageGenericResult.page(),
        pageGenericResult.size(),
        pageGenericResult.totalItems(),
        pageGenericResult.totalPages(),
        pageGenericResult.hasNext(),
        pageGenericResult.hasPrevious()
    );

   return new PaginationResponse<>(data, meta);
  }
}