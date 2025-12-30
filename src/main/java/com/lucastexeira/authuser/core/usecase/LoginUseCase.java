package com.lucastexeira.authuser.core.usecase;

import com.lucastexeira.authuser.core.exception.WrongCredentialsException;
import com.lucastexeira.authuser.core.port.in.LoginInputPort;
import com.lucastexeira.authuser.core.port.out.FindUserByEmailOutputPort;
import com.lucastexeira.authuser.core.port.out.PasswordEncoderOutputPort;
import com.lucastexeira.authuser.core.port.out.TokenServiceOutputPort;
import com.lucastexeira.authuser.core.usecase.result.AuthResult;
import com.lucastexeira.authuser.core.domain.User;

public class LoginUseCase implements LoginInputPort {

  private final FindUserByEmailOutputPort findUserByEmail;

  private final PasswordEncoderOutputPort passwordEncoder;

  private final TokenServiceOutputPort tokenService;

  public LoginUseCase(FindUserByEmailOutputPort findUserByEmail, PasswordEncoderOutputPort passwordEncoder, TokenServiceOutputPort tokenService) {
    this.findUserByEmail = findUserByEmail;
    this.passwordEncoder = passwordEncoder;
    this.tokenService = tokenService;
  }

  @Override
  public AuthResult execute(String email, String password) {
    User user = findUserByEmail.execute(email);

    if(user == null) {
      throw new WrongCredentialsException("Wrong credentials");
    }

    if (!user.passwordMatches(password, passwordEncoder)) {
      throw new WrongCredentialsException("Wrong credentials");
    }

    String token = tokenService.generateToken(user);
    String refreshToken = tokenService.generateRefreshToken(user);

    return new AuthResult(token, refreshToken);
  }
}
