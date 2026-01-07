package com.lucastexeira.authuser.adapters.out.messaging;

import com.lucastexeira.authuser.core.notification.WhatsAppNotificationMessage;
import com.lucastexeira.authuser.core.port.out.notification.NotificationQueueOutputPort;
import com.lucastexeira.authuser.infra.messaging.rabbitmq.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQNotificationAdapterOutput implements
    NotificationQueueOutputPort {

  private final RabbitTemplate rabbitTemplate;

  public RabbitMQNotificationAdapterOutput(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }


  @Override
  public void enqueue(WhatsAppNotificationMessage message) {
    rabbitTemplate.convertAndSend(
        RabbitMQConfig.WHATSAPP_EXCHANGE,
        RabbitMQConfig.WHATSAPP_ROUTING_KEY,
        message
    );
  }
}
