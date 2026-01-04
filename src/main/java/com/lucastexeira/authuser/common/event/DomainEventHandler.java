package com.lucastexeira.authuser.common.event;

public interface DomainEventHandler<T extends DomainEvent> {
  void handle(T event);
}
