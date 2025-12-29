package com.lucastexeira.authuser.core.usecase.result;

public class AuthResult {

  private final String accessToken;

  public AuthResult(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getAccessToken() {
    return accessToken;
  }
}