package com.lucastexeira.authuser.core.port.in.whatsapp;

import java.util.UUID;

public interface ConnectWhatsappSessionInputPort {

  public void execute(UUID userId);
}
