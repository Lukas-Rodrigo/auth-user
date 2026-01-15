package com.lucastexeira.authuser.core.port.in.whatsapp;

import com.lucastexeira.authuser.common.enums.WhatsappSessionStatus;

import java.util.UUID;

public interface CreateWhatsappSessionInputPort {
  public void execute(UUID userId, WhatsappSessionStatus status);

}
