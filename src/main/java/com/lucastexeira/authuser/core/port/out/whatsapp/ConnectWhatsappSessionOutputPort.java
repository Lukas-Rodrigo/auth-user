package com.lucastexeira.authuser.core.port.out.whatsapp;

import com.lucastexeira.authuser.core.domain.whasapp.WhatsappSession;

public interface ConnectWhatsappSessionOutputPort {

  WhatsappSession execute(WhatsappSession whatsappSession);
}
