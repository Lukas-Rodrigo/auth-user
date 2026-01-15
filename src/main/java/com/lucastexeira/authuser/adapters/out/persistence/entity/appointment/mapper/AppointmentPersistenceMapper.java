package com.lucastexeira.authuser.adapters.out.persistence.entity.appointment.mapper;

import com.lucastexeira.authuser.adapters.out.persistence.entity.appointment.AppointmentEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.association.AppointmentServiceEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.businesservice.BusinessServiceEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.client.ClientEntity;
import com.lucastexeira.authuser.core.domain.appointment.Appointment;
import com.lucastexeira.authuser.core.domain.appointment.AppointmentService;
import com.lucastexeira.authuser.core.domain.valueobject.Money;
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

import java.math.BigDecimal;
import java.util.ArrayList;
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

  @AfterMapping
  protected void mapServicesToDomain(
      AppointmentEntity entity,
      @MappingTarget Appointment domain
  ) {
    if (entity.getServices() != null && !entity.getServices().isEmpty()) {
      List<AppointmentService> domainServices = new ArrayList<>();

      entity.getServices().forEach(serviceEntity -> {
        AppointmentService appointmentService = new AppointmentService();
        appointmentService.setAppointmentId(domain.getId());
        appointmentService.setBusinessServiceId(serviceEntity.getBusinessService().getId());
        appointmentService.setAppliedPrice(new Money(new BigDecimal(serviceEntity.getAppliedPrice())));
        appointmentService.setDiscount(
            serviceEntity.getDiscount() != null && serviceEntity.getDiscount() > 0
                ? new Money(new BigDecimal(serviceEntity.getDiscount()))
                : Money.zero()
        );
        appointmentService.setDuration(serviceEntity.getBusinessService().getDuration());

        domainServices.add(appointmentService);
      });

      domain.setServices(domainServices);
    }
  }

  @Mapping(target = "services", ignore = true)
  @Mapping(target = "client", expression = "java(mapClient(appointment.getClientId()))")
  public abstract AppointmentEntity toEntity(Appointment appointment);

  public List<Appointment> toDomainList(List<AppointmentEntity> appointmentEntities) {
    return appointmentEntities.stream()
        .map(this::toDomain)
        .toList();
  }

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
            serviceEntity.setAppliedPrice(service.getAppliedPrice().getAmount().intValue());
            serviceEntity.setDiscount(service.getDiscount() != null ? service.getDiscount().getAmount().intValue() : 0);

            Money discount = service.getDiscount() != null ? service.getDiscount() : Money.zero();
            int finalPrice = service.getAppliedPrice().subtract(discount).getAmount().intValue();
            serviceEntity.setFinalPrice(finalPrice);

            return serviceEntity;
          })
          .collect(Collectors.toList());

      entity.setServices(serviceEntities);
    }

    entity.setTotalPrice(domain.getTotalPrice().getAmount());
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
            service.getServiceName(),
            service.getServicePrice().intValue(),
            service.getDuration().intValue()
        )).toList();

    return new AppointmentDetailsResult(
        appointment.getId(),
        appointment.getScheduledAt(),
        appointment.getStatus().getStatus(),
        new ClientResult(appointment.getClient().getName(),
            appointment.getClient().getPhoneNumber(),
            appointment.getClient().getObservations()),
        serviceResultList,
        appointment.getTotalPrice().intValue(),
        appointment.getCreatedAt()
    );
  }

  protected Money map(BigDecimal value) {
    return value != null ? new Money(value) : null;
  }

  protected BigDecimal map(Money value) {
    return value != null ? value.getAmount() : null;
  }
}
