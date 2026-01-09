package com.lucastexeira.authuser.adapters.out.appointment;

import com.lucastexeira.authuser.adapters.in.controllers.appointment.mapper.AppointmentMapper;
import com.lucastexeira.authuser.adapters.out.persistence.entity.appointment.mapper.AppointmentPersistenceMapper;
import com.lucastexeira.authuser.adapters.out.persistence.repository.AppointmentRepository;
import com.lucastexeira.authuser.adapters.out.persistence.repository.BusinessServiceRepository;
import com.lucastexeira.authuser.core.domain.appointment.Appointment;
import com.lucastexeira.authuser.core.port.out.appointment.FindActiveAppointmentByUserAndDayOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class FindActiveAppointmentByUserAndDayAdapter implements
    FindActiveAppointmentByUserAndDayOutputPort {

  private final AppointmentPersistenceMapper appointmentMapper;
  private final AppointmentRepository appointmentRepository;

  public FindActiveAppointmentByUserAndDayAdapter(
      AppointmentPersistenceMapper appointmentMapper,
      AppointmentRepository appointmentRepository
  ) {
    this.appointmentMapper = appointmentMapper;
    this.appointmentRepository = appointmentRepository;
  }

  @Override
  public List<Appointment> execute(
      UUID userId,
      LocalDateTime startOfDay,
      LocalDateTime endOfDay
  ) {
    var appointmentEntities = this.appointmentRepository.findActiveAppointmentsByUserAndDay(
      userId,
      startOfDay,
      endOfDay
    );
    return appointmentMapper.toDomainList(appointmentEntities);
  }
}
