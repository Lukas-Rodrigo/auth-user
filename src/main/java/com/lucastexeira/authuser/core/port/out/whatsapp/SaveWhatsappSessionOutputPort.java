package com.lucastexeira.authuser.core.port.out.whatsapp;

import com.lucastexeira.authuser.core.domain.whasapp.WhatsappSession;

public interface SaveWhatsappSessionOutputPort {

  WhatsappSession execute(WhatsappSession whatsappSession);
}
