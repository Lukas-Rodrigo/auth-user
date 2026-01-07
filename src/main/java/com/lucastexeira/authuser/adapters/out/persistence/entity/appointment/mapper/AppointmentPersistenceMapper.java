package com.lucastexeira.authuser.adapters.out.persistence.entity.appointment.mapper;

import com.lucastexeira.authuser.adapters.out.persistence.entity.appointment.AppointmentEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.association.AppointmentServiceEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.businesservice.BusinessServiceEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.client.ClientEntity;
import com.lucastexeira.authuser.core.domain.appointment.Appointment;
import com.lucastexeira.authuser.core.usecase.result.AppointmentDetailsResult;
import com.lucastexeira.authuser.core.usecase.result.BusinessServiceResult;
import com.lucastexeira.authuser.core.usecase.result.ClientResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Component
public abstract class AppointmentPersistenceMapper {

  @PersistenceContext
  protected EntityManager entityManager;

  @Mapping(target = "services", ignore = true)
  @Mapping(target = "clientId", expression = "java(appointmentEntity.getClient() != null ? appointmentEntity.getClient().getId() : null)")
  public abstract Appointment toDomain(AppointmentEntity appointmentEntity);

  @Mapping(target = "services", ignore = true)
  @Mapping(target = "client", expression = "java(mapClient(appointment.getClientId()))")
  public abstract AppointmentEntity toEntity(Appointment appointment);

  @AfterMapping
  protected void mapServices(
      @MappingTarget AppointmentEntity entity,
      Appointment domain
  ) {
    if (domain.getServices() != null && !domain.getServices().isEmpty()) {
      List<AppointmentServiceEntity> serviceEntities = domain.getServices()
          .stream()
          .map(service -> {
            AppointmentServiceEntity serviceEntity = new AppointmentServiceEntity();
            serviceEntity.setAppointment(entity);

            BusinessServiceEntity businessService = entityManager.getReference(
                BusinessServiceEntity.class, service.getBusinessServiceId());
            serviceEntity.setBusinessService(businessService);
            serviceEntity.setAppliedPrice(service.getAppliedPrice());
            serviceEntity.setDiscount(service.getDiscount());

            int finalPrice = service.getAppliedPrice() - (service.getDiscount() != null ? service.getDiscount() : 0);
            serviceEntity.setFinalPrice(finalPrice);

            return serviceEntity;
          })
          .collect(Collectors.toList());

      entity.setServices(serviceEntities);
    }

    entity.setTotalPrice(domain.getTotalPrice());
  }

  protected ClientEntity mapClient(UUID clientId) {
    if (clientId == null) {
      return null;
    }
    return entityManager.getReference(ClientEntity.class, clientId);
  }

  public AppointmentDetailsResult toAppointmentDetails(AppointmentEntity appointment) {
    var serviceResultList =
        appointment.getServices().stream().map(service -> new BusinessServiceResult(
            service.getServiceName(), service.getServicePrice(), service.getDuration()
        )).toList();

    return new AppointmentDetailsResult(
        appointment.getId(),
        appointment.getScheduledAt(),
        appointment.getStatus().getStatus(),
        new ClientResult(appointment.getClient().getName(),
            appointment.getClient().getPhoneNumber(),
            appointment.getClient().getObservations()),
        serviceResultList,
        appointment.getTotalPrice(),
        appointment.getCreatedAt()
    );
  }
}
