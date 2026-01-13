package com.lucastexeira.authuser.core.port.out.notification;

import com.lucastexeira.authuser.core.notification.WhatsAppNotificationMessage;

public interface NotificationQueueOutputPort {
  void enqueue(WhatsAppNotificationMessage message);
}