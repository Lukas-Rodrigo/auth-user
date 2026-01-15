package com.lucastexeira.authuser.core.handler;

import com.lucastexeira.authuser.common.event.DomainEventHandler;
import com.lucastexeira.authuser.core.domain.appointment.event.AppointmentScheduledEvent;
import com.lucastexeira.authuser.core.notification.AppointmentMessageData;
import com.lucastexeira.authuser.core.notification.AppointmentMessageTemplate;
import com.lucastexeira.authuser.core.notification.WhatsAppNotificationMessage;
import com.lucastexeira.authuser.core.port.in.client.FindClientByIdOutputPort;
import com.lucastexeira.authuser.core.port.out.businessservices.FindBusinessServiceByIdOutputPort;
import com.lucastexeira.authuser.core.port.out.notification.NotificationQueueOutputPort;

import java.util.List;

public class AppointmentScheduledEventHandler implements
    DomainEventHandler<AppointmentScheduledEvent> {

  private final NotificationQueueOutputPort notificationQueueOutputPort;

  private final FindClientByIdOutputPort findClientByIdOutputPort;

  private final FindBusinessServiceByIdOutputPort findBusinessServiceByIdOutputPort;

  public AppointmentScheduledEventHandler(
      NotificationQueueOutputPort notificationQueueOutputPort,
      FindClientByIdOutputPort findClientByIdOutputPort,
      FindBusinessServiceByIdOutputPort findBusinessServiceByIdOutputPort
  ) {
    this.notificationQueueOutputPort = notificationQueueOutputPort;
    this.findClientByIdOutputPort = findClientByIdOutputPort;
    this.findBusinessServiceByIdOutputPort = findBusinessServiceByIdOutputPort;
  }

  @Override
  public void handle(AppointmentScheduledEvent event) {

    var client = this.findClientByIdOutputPort.execute(event.getAppointment()
        .getClientId());

    var serviceNames =
        event.getAppointment().getServices().stream().map(item -> {
      return this.findBusinessServiceByIdOutputPort.execute(item.getBusinessServiceId()).getName();
    }).toList();
    var servicesFormatted = AppointmentMessageTemplate.formatServiceNames(serviceNames);
    var appointmentMessageData = new AppointmentMessageData(
        client.getName(),
        servicesFormatted,
        event.getAppointment().getScheduledAt(),
        event.getAppointment().getStatus(),
        event.getAppointment().getTotalPrice().getAmount()
    );

    var message = AppointmentMessageTemplate.build(appointmentMessageData);

    notificationQueueOutputPort.enqueue(
        new WhatsAppNotificationMessage(
            client.getUserId(),
            client.getPhoneNumber(),
            message,
            event.getAppointment().getId()
        )
    );
  }


}
