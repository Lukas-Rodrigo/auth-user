package com.lucastexeira.authuser.infra.handler.model;

import java.util.Map;

public record ErrorResponseHandler(
    boolean error,
    int status,
    String message,
    Map<String, String> details
) {

  public static ErrorResponseHandler of(Map<String, String> details,
                                        int status, String message) {
    return new ErrorResponseHandler(
        true,
        status,
        message,
        details
    );
  }

  public static ErrorResponseHandler validationError(Map<String, String> details) {
    return new ErrorResponseHandler(
        true,
        400,
        "Validation Error",
        details
    );
  }
}
