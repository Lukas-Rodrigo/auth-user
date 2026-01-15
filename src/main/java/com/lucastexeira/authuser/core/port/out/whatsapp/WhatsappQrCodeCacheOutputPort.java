package com.lucastexeira.authuser.core.port.out.whatsapp;

import java.util.UUID;

public interface WhatsappQrCodeCacheOutputPort {

  void save(UUID userId, String qrCode);

  String get(UUID userId);
  void delete(UUID userId);


}
