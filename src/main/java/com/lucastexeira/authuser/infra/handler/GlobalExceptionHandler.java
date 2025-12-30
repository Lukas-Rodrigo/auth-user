package com.lucastexeira.authuser.infra.handler;

import com.lucastexeira.authuser.core.exception.UserAlreadyExistsException;
import com.lucastexeira.authuser.core.exception.WrongCredentialsException;
import com.lucastexeira.authuser.infra.handler.model.ErrorResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ErrorResponseHandler> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
    Map<String, String> datails = Map.of("user", ex.getMessage());
    ErrorResponseHandler errorResponse = ErrorResponseHandler.of(datails,
        HttpStatus.BAD_REQUEST.value(), ex.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  @ExceptionHandler(WrongCredentialsException.class)
  public ResponseEntity<ErrorResponseHandler> handleWrongCredentials(WrongCredentialsException ex) {
    Map<String, String> datails = Map.of("credentials", ex.getMessage());
    ErrorResponseHandler errorResponse = ErrorResponseHandler.of(datails,
        HttpStatus.BAD_REQUEST.value(), ex.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }


  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseHandler> handleValidationErrors(MethodArgumentNotValidException ex) {
    Map<String, String> fieldErrors =
        ex.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.
                toMap(FieldError::getField, FieldError::getDefaultMessage,
                    (msg1, msg2) -> msg1));
    ErrorResponseHandler errorResponse =
        ErrorResponseHandler.validationError(fieldErrors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }


}
