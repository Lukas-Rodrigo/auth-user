package com.lucastexeira.authuser.core.port.out;

public interface PasswordEncoderOutputPort {

  boolean matches(String rawPassword, String encodedPassword);

  String encode(String rawPassword);
}
