package com.lucastexeira.authuser.common.event;

import java.util.Map;

public class SimpleDomainEventPublisher implements DomainEventPublisher {

  private final Map<Class<?>, DomainEventHandler<?>> handlers;

  public SimpleDomainEventPublisher(
      Map<Class<?>, DomainEventHandler<?>> handlers
  ) {
    this.handlers = handlers;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void publish(DomainEvent event) {
    DomainEventHandler handler = handlers.get(event.getClass());

    if (handler != null) {
      handler.handle(event);
    }
  }
}
