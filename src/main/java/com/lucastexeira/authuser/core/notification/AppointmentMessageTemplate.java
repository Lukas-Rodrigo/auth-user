package com.lucastexeira.authuser.core.notification;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class AppointmentMessageTemplate {

  public static String build(AppointmentMessageData data) {
    return switch (data.status()) {
      case CONFIRMED -> confirmed(data);
      case PENDING -> pending(data);
      case COMPLETED -> completed(data);
      case CANCELED -> canceled(data);
    };
  }

  private static String confirmed(AppointmentMessageData data) {
    return """
                AGENDAMENTO CONFIRMADO

                Cliente: %s
                Serviços: %s
                Data: %s
                Horário: %s
                Status: Confirmado
                Total: %s

                Você receberá um lembrete próximo ao horário.
                """.formatted(
        data.clientName(),
        data.serviceName(),
        formatDate(data.dateTime()),
        formatTime(data.dateTime()),
        formatMoney(data.total())
    );
  }

  private static String pending(AppointmentMessageData data) {
    return """
                AGENDAMENTO PENDENTE

                Cliente: %s
                Serviços: %s
                Data: %s
                Horário: %s
                Status: Pendente
                Total: %s

                Aguardando confirmação.
                """.formatted(
        data.clientName(),
        data.serviceName(),
        formatDate(data.dateTime()),
        formatTime(data.dateTime()),
        formatMoney(data.total())
    );
  }

  private static String completed(AppointmentMessageData data) {
    return """
                AGENDAMENTO CONCLUÍDO

                Cliente: %s
                Serviços: %s
                Data: %s
                Horário: %s
                Total: %s

                Obrigado pela preferência!
                """.formatted(
        data.clientName(),
        data.serviceName(),
        formatDate(data.dateTime()),
        formatTime(data.dateTime()),
        formatMoney(data.total())
    );
  }

  private static String canceled(AppointmentMessageData data) {
    return """
                AGENDAMENTO CANCELADO

                Cliente: %s
                Serviços: %s
                Data: %s
                Horário: %s

                Entre em contato para reagendar.
                """.formatted(
        data.clientName(),
        data.serviceName(),
        formatDate(data.dateTime()),
        formatTime(data.dateTime())
    );
  }

  // ===== Helpers =====


  public static String formatServiceNames(List<String> services) {
    if (services == null || services.isEmpty()) {
      return "-";
    }

    if (services.size() == 1) {
      return services.get(0);
    }

    if (services.size() == 2) {
      return services.get(0) + " e " + services.get(1);
    }

    return String.join(", ", services.subList(0, services.size() - 1))
        + " e "
        + services.get(services.size() - 1);
  }

  private static String formatDate(LocalDateTime dateTime) {
    return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }

  private static String formatTime(LocalDateTime dateTime) {
    return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
  }

  private static String formatMoney(BigDecimal value) {
    return NumberFormat
        .getCurrencyInstance(new Locale("pt", "BR"))
        .format(value);
  }
}

