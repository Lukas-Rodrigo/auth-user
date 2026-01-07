package com.lucastexeira.authuser.core.notification;

import java.util.UUID;

public record WhatsAppNotificationMessage(
    UUID userId,
    String message
) {
}
