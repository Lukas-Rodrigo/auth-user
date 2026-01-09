package com.lucastexeira.authuser.adapters.out.appointment;

import com.lucastexeira.authuser.adapters.out.persistence.entity.appointment.AppointmentEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.appointment.mapper.AppointmentPersistenceMapper;
import com.lucastexeira.authuser.adapters.out.persistence.repository.AppointmentRepository;
import com.lucastexeira.authuser.core.port.out.appointment.FetchAppointmentOutputPort;
import com.lucastexeira.authuser.core.usecase.result.AppointmentDetailsResult;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class FetchAppointmentAdapter implements FetchAppointmentOutputPort {

  private final AppointmentRepository appointmentRepository;

  private final AppointmentPersistenceMapper appointmentMapper;

  public FetchAppointmentAdapter(AppointmentRepository appointmentRepository,
   AppointmentPersistenceMapper appointmentPersistenceMapper) {
    this.appointmentRepository = appointmentRepository;
    this.appointmentMapper = appointmentPersistenceMapper;
  }

  @Override
  public PageGenericResult<AppointmentDetailsResult> execute(
      UUID userId,
      int page,
      int size,
      LocalDate startDate,
      LocalDate endDate
  ) {
    Pageable pageable = PageRequest.of(page, size);

    Page<AppointmentEntity> result = appointmentRepository.findAllWithFilter(
        userId,
        startDate,
        endDate,
        pageable
    );

    return new PageGenericResult<AppointmentDetailsResult>(
        result.getContent()
            .stream()
            .map(appointmentMapper::toAppointmentDetails)
            .toList(),
        result.getNumber(),
        result.getSize(),
        result.getTotalElements(),
        result.getTotalPages(),
        result.hasNext(),
        result.hasPrevious()
    );
  }
}
