package com.lucastexeira.authuser.core.port.out.authentication;

import com.lucastexeira.authuser.core.domain.User;

public interface TokenServiceOutputPort {

  String generateToken(User user);

  String generateRefreshToken(User user);

  String getUserIdFromRefreshToken(String refreshToken);

  boolean isRefreshTokenValid(String refreshToken);

  boolean isTokenValid(String token);
}
