package com.lucastexeira.authuser.core.port.in.whatsapp;

import java.util.UUID;

public interface StoreWhatsappQrCodeInputPort {
  void store(UUID userId, String qrCode);
}
