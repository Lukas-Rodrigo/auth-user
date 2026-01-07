package com.lucastexeira.authuser.infra.messaging.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  public static final String WHATSAPP_EXCHANGE = "whatsapp.exchange";
  public static final String WHATSAPP_QUEUE = "whatsapp.notification.queue";
  public static final String WHATSAPP_ROUTING_KEY = "whatsapp.notification";

  @Bean
  public DirectExchange whatsappExchange() {
    return new DirectExchange(WHATSAPP_EXCHANGE);
  }

  @Bean
  public Queue whatsappQueue() {
    return QueueBuilder.durable(WHATSAPP_QUEUE).build();
  }

  @Bean
  public Binding whatsappBinding() {
    return BindingBuilder
        .bind(whatsappQueue())
        .to(whatsappExchange())
        .with(WHATSAPP_ROUTING_KEY);
  }


  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}

