package com.lucastexeira.authuser.adapters.out.appointment;

import com.lucastexeira.authuser.adapters.out.persistence.entity.appointment.AppointmentEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.appointment.mapper.AppointmentPersistenceMapper;
import com.lucastexeira.authuser.adapters.out.persistence.repository.AppointmentRepository;
import com.lucastexeira.authuser.core.domain.appointment.Appointment;
import com.lucastexeira.authuser.core.port.out.appointment.SaveAppointmentOutputPort;
import org.springframework.stereotype.Component;

@Component
public class SaveAppointmentAdapter implements SaveAppointmentOutputPort {

  private final AppointmentRepository appointmentRepository;
  private final AppointmentPersistenceMapper appointmentMapper;

  public SaveAppointmentAdapter(
      AppointmentRepository appointmentRepository,
      AppointmentPersistenceMapper appointmentMapper
  ) {
    this.appointmentRepository = appointmentRepository;
    this.appointmentMapper = appointmentMapper;
  }

  @Override
  public Appointment execute(Appointment appointment) {
    AppointmentEntity appointmentEntity = appointmentMapper.toEntity(
        appointment);
    AppointmentEntity savedEntity = appointmentRepository.save(
        appointmentEntity);
    return appointmentMapper.toDomain(savedEntity);
  }
}
