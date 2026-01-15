package com.lucastexeira.authuser.integrations.factory;

import com.lucastexeira.authuser.adapters.out.persistence.entity.appointment.AppointmentEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.association.AppointmentServiceEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.businesservice.BusinessServiceEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.client.ClientEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.user.UserEntity;
import com.lucastexeira.authuser.adapters.out.persistence.repository.AppointmentRepository;
import com.lucastexeira.authuser.common.enums.AppointmentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AppointmentTestFactory {

  public static AppointmentEntity createAppointmentEntity(
      AppointmentRepository appointmentRepository,
      UserEntity user,
      BusinessServiceEntity businessService,
      ClientEntity client

  ) {
    AppointmentEntity appointment = new AppointmentEntity(
        UUID.randomUUID(),
        LocalDateTime.now().plusDays(1),
        AppointmentStatus.PENDING,
        LocalDate.now(),
        null,
        client,
        null,
        BigDecimal.ZERO
    );
    AppointmentServiceEntity appointmentService = new AppointmentServiceEntity(
       null,
        appointment,
        businessService,
        0,
        0,
        0
    );
    appointment.setServices(List.of(appointmentService));
    return appointmentRepository.save(appointment);
  }
}
