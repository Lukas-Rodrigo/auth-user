package com.lucastexeira.authuser.core.port.out.client;

import java.util.UUID;

public interface ExistsByIdAndUserIdOutputPort {
  boolean execute(UUID clientId, UUID userId);
}
