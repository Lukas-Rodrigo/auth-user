package com.lucastexeira.authuser.core.port.out.authentication;

public interface PasswordEncoderOutputPort {

  boolean matches(
      String rawPassword,
      String encodedPassword
  );

  String encode(String rawPassword);
}
