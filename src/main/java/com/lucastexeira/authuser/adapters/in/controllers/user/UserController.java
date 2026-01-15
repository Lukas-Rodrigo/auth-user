package com.lucastexeira.authuser.adapters.in.controllers.user;

import com.lucastexeira.authuser.adapters.in.controllers.user.dto.LoginRequest;
import com.lucastexeira.authuser.adapters.in.controllers.user.dto.UserRequest;
import com.lucastexeira.authuser.adapters.in.controllers.user.dto.UserResponse;
import com.lucastexeira.authuser.adapters.in.controllers.user.mapper.UserMapper;
import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.port.in.user.CreateUserInputPort;
import com.lucastexeira.authuser.core.port.in.authenticate.LoginInputPort;
import com.lucastexeira.authuser.core.port.in.authenticate.RefreshTokenInputPort;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("api/auth")
public class UserController {

  private final CreateUserInputPort createUserInputPort;

  private final LoginInputPort loginInputPort;

  private final RefreshTokenInputPort refreshTokenInputPort;

  public UserController(
      CreateUserInputPort createUserInputPort,
      LoginInputPort loginInputPort,
      RefreshTokenInputPort refreshTokenInputPort
  ) {
    this.loginInputPort = loginInputPort;
    this.createUserInputPort = createUserInputPort;
    this.refreshTokenInputPort = refreshTokenInputPort;
  }

  @PostMapping("/users")
  public ResponseEntity<UserResponse> create(
      @Valid @RequestBody UserRequest user
  ) {
    User newUser = UserMapper.INSTANCE.toDomain(user);

    var createdUser = createUserInputPort.createUser(newUser);
    var response = UserMapper.INSTANCE.toResponseDTO(createdUser);
    return ResponseEntity.ok().body(response);

  }

  @PostMapping("/login")
  public ResponseEntity<Void> login(
      @Valid @RequestBody() LoginRequest loginRequest,
      HttpServletResponse response
  ) {
    var token = loginInputPort.execute(loginRequest.email(),
        loginRequest.password());

    var cookieAccessToken = buildCookie(token.getAccessToken(), "access_token"
        , 7);
    var cookieRefreshToken = buildCookie(token.getRefreshToken(),
        "refresh_token"
        , 7);
    response.addHeader(HttpHeaders.SET_COOKIE, cookieAccessToken.toString());
    response.addHeader(HttpHeaders.SET_COOKIE, cookieRefreshToken.toString());
    return ResponseEntity.ok().build();
  }


  @PostMapping("/refresh")
  public ResponseEntity<Void> refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) {
    String refreshToken = this.extractFromCookie(request, "refresh_token");

    String accessToken =
        this.refreshTokenInputPort
            .execute(refreshToken);

    var cookieRefreshToken = buildCookie(accessToken, "access_token", 7);

    response.addHeader(HttpHeaders.SET_COOKIE, cookieRefreshToken.toString());
    return ResponseEntity.ok().build();
  }

  private ResponseCookie buildCookie(
      String token,
      String nameCookie,
      int days
  ) {
    return ResponseCookie.from(nameCookie, token)
        .httpOnly(true)
        .secure(false) // in production, set to true
        .maxAge(days * 24L * 60 * 60) // days
        .sameSite("Lax")
        .path("/")
        .build();
  }

  private String extractFromCookie(
      HttpServletRequest request,
      String nameCookie
  ) {
    if (request.getCookies() == null) {
      return null;
    }

    return Arrays.stream(request.getCookies())
        .filter(cookie -> nameCookie.equals(cookie.getName()))
        .map(Cookie::getValue)
        .findFirst()
        .orElse(null);
  }

}
