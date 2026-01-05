package com.lucastexeira.authuser.adapters.out.cryptography;

import com.lucastexeira.authuser.core.port.out.authentication.PasswordEncoderOutputPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class PasswordEncoderAdapter implements PasswordEncoderOutputPort {

  private final PasswordEncoder encoder;

  public PasswordEncoderAdapter(PasswordEncoder encoder) {
    this.encoder = encoder;
  }


  @Override
  public boolean matches(
      String rawPassword,
      String encodedPassword
  ) {
    return encoder.matches(rawPassword, encodedPassword);
  }

  @Override
  public String encode(String rawPassword) {
    return encoder.encode(rawPassword);
  }
}
