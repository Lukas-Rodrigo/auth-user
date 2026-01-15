package com.lucastexeira.authuser.core.port.out.whatsapp;

import com.lucastexeira.authuser.core.domain.whasapp.WhatsappSession;

import java.util.UUID;

public interface FindWhatsappSessionByUserOutputPort {

  WhatsappSession execute(UUID userId);
}
