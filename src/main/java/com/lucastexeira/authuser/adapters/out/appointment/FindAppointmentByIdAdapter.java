package com.lucastexeira.authuser.adapters.out.appointment;

import com.lucastexeira.authuser.adapters.out.persistence.entity.appointment.AppointmentEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.appointment.mapper.AppointmentPersistenceMapper;
import com.lucastexeira.authuser.adapters.out.persistence.repository.AppointmentRepository;
import com.lucastexeira.authuser.core.domain.appointment.Appointment;
import com.lucastexeira.authuser.core.port.out.appointment.FindAppointmentByIdOutputPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FindAppointmentByIdAdapter implements
    FindAppointmentByIdOutputPort {

  private final AppointmentRepository appointmentRepository;
  private final AppointmentPersistenceMapper appointmentMapper;

  public FindAppointmentByIdAdapter(
      AppointmentRepository appointmentRepository,
      AppointmentPersistenceMapper appointmentMapper
  ) {
    this.appointmentRepository = appointmentRepository;
    this.appointmentMapper = appointmentMapper;
  }

  @Override
  public Appointment execute(UUID appointmentId) {
    AppointmentEntity appointmentEntity =
        appointmentRepository.findById(appointmentId).isPresent()
        ? appointmentRepository.findById(appointmentId).get()
        : null;
    return appointmentMapper.toDomain(appointmentEntity);
  }
}
