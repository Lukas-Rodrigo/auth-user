package com.lucastexeira.authuser.infra.config;

import com.lucastexeira.authuser.common.event.DomainEventPublisher;
import com.lucastexeira.authuser.common.event.SimpleDomainEventPublisher;
import com.lucastexeira.authuser.core.domain.appointment.event.AppointmentScheduledEvent;
import com.lucastexeira.authuser.core.domain.appointment.event.AppointmentStatusEvent;
import com.lucastexeira.authuser.core.handler.AppointmentScheduledEventHandler;
import com.lucastexeira.authuser.core.handler.AppointmentStatusEventHandler;
import com.lucastexeira.authuser.core.port.out.notification.NotificationQueueOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class DomainEventConfig {

  @Bean
  public AppointmentScheduledEventHandler appointmentScheduledEventHandler(NotificationQueueOutputPort notificationQueueOutputPort) {
    return new AppointmentScheduledEventHandler(notificationQueueOutputPort);
  }

  @Bean
  public AppointmentStatusEventHandler appointmentStatusEventHandler() {
    return new AppointmentStatusEventHandler();
  }

  @Bean
  public DomainEventPublisher domainEventPublisher(
      AppointmentScheduledEventHandler appointmentScheduledEventHandler,
      AppointmentStatusEventHandler appointmentStatusEventHandler
  ) {
    return new SimpleDomainEventPublisher(
        Map.of(AppointmentScheduledEvent.class,
            appointmentScheduledEventHandler, AppointmentStatusEvent.class,
            appointmentStatusEventHandler));
  }


}
