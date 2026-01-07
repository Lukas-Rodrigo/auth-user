package com.lucastexeira.authuser.common.event;

import java.time.Instant;

public interface DomainEvent {
  Instant occurredOn();
}
