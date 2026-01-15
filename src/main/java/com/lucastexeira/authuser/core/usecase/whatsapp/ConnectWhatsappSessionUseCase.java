package com.lucastexeira.authuser.core.usecase.whatsapp;

import com.lucastexeira.authuser.common.enums.WhatsappSessionStatus;
import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.domain.whasapp.WhatsappSession;
import com.lucastexeira.authuser.core.exception.UserNotFoundException;
import com.lucastexeira.authuser.core.notification.CreateWhatsappSessionMessage;
import com.lucastexeira.authuser.core.port.in.whatsapp.ConnectWhatsappSessionInputPort;
import com.lucastexeira.authuser.core.port.out.notification.NotificationQueueOutputPort;
import com.lucastexeira.authuser.core.port.out.user.FindUserByIdOutputPort;
import com.lucastexeira.authuser.core.port.out.whatsapp.FindWhatsappSessionByUserOutputPort;
import com.lucastexeira.authuser.core.port.out.whatsapp.SaveWhatsappSessionOutputPort;

import java.util.UUID;

public class ConnectWhatsappSessionUseCase implements
    ConnectWhatsappSessionInputPort {
  private final FindWhatsappSessionByUserOutputPort findWhatsappSessionByUserOutputPort;

  private final FindUserByIdOutputPort findUserByIdOutputPort;

  private final SaveWhatsappSessionOutputPort saveWhatsappSessionOutputPort;


  private final NotificationQueueOutputPort notificationQueueOutputPort;

  public ConnectWhatsappSessionUseCase(
      FindWhatsappSessionByUserOutputPort findWhatsappSessionByUserOutputPort,
      FindUserByIdOutputPort findUserByIdOutputPort,
      SaveWhatsappSessionOutputPort saveWhatsappSessionOutputPort,
      NotificationQueueOutputPort notificationQueueOutputPort
  ) {
    this.findWhatsappSessionByUserOutputPort = findWhatsappSessionByUserOutputPort;
    this.findUserByIdOutputPort = findUserByIdOutputPort;
    this.saveWhatsappSessionOutputPort = saveWhatsappSessionOutputPort;
    this.notificationQueueOutputPort = notificationQueueOutputPort;
  }

  public void execute(UUID userId) {

    User isValidUser =
        findUserByIdOutputPort.execute(userId);

    if (isValidUser == null) {
      throw new UserNotFoundException(userId);
    }

    WhatsappSession existingSession =
        findWhatsappSessionByUserOutputPort.execute(userId);

    if (existingSession != null) {

      if (existingSession.isConnected()) {
        throw new RuntimeException("User already has a connected Whatsapp session.");
      }

      if (existingSession.isQrGenerated()) {
        throw new RuntimeException("QR code already generated for this user.");
      }

      // Já existe uma sessão, não precisa criar uma nova
      System.out.println("Whatsapp session already exists for user: " + userId + ". Skipping creation.");
      existingSession.updateStats(WhatsappSessionStatus.QR_GENERATED);
      this.saveWhatsappSessionOutputPort.execute(existingSession);
      notificationQueueOutputPort.queue(
          new CreateWhatsappSessionMessage(userId)
      );
      return;
    }

    System.out.println("Queueing Whatsapp session creation for user: " + userId);
    var newSession = new WhatsappSession(
        userId
    );
    this.saveWhatsappSessionOutputPort.execute(newSession);

    notificationQueueOutputPort.queue(
        new CreateWhatsappSessionMessage(userId)
    );
  }
}
