package com.lucastexeira.authuser.core.usecase;

import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.exception.InvalidRefreshTokenException;
import com.lucastexeira.authuser.core.exception.UserNotFoundException;
import com.lucastexeira.authuser.core.port.in.RefreshTokenInputPort;
import com.lucastexeira.authuser.core.port.out.FindUserByIdOutputPort;
import com.lucastexeira.authuser.core.port.out.TokenServiceOutputPort;

import java.util.UUID;

public class RefreshTokenUseCase implements RefreshTokenInputPort {

  private final FindUserByIdOutputPort findUserByIdOutputPort;

  private final TokenServiceOutputPort tokenService;

  public RefreshTokenUseCase(FindUserByIdOutputPort findUserByIdOutputPort,
      TokenServiceOutputPort tokenService) {
    this.findUserByIdOutputPort = findUserByIdOutputPort;
    this.tokenService = tokenService;
  }

  @Override
  public String execute(String refreshToken) {
    if(!this.tokenService.isRefreshTokenValid(refreshToken)) {
      throw new InvalidRefreshTokenException("Invalid refresh token error.");
    }

    String userId =
        this.tokenService.getUserIdFromRefreshToken(refreshToken);

    User user = this.findUserByIdOutputPort.execute(UUID.fromString(userId));

    if (user == null) {
      throw new UserNotFoundException("User not found error.");
    }

    return tokenService.generateToken(user);
  }
}
