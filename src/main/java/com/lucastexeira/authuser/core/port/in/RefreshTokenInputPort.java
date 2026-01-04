package com.lucastexeira.authuser.core.port.in;

public interface RefreshTokenInputPort {

  String execute(String refreshToken);
}
