package com.lucastexeira.authuser.core.notification;

import com.lucastexeira.authuser.common.enums.AppointmentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AppointmentMessageData(
    String clientName,
    String serviceName,
    LocalDateTime dateTime,
    AppointmentStatus status,
    BigDecimal total
) {}
