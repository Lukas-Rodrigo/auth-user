package com.lucastexeira.authuser.adapters.out.jwt;

import com.lucastexeira.authuser.core.port.out.TokenServiceOutputPort;
import com.lucastexeira.authuser.core.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenServiceAdapter implements TokenServiceOutputPort {

  @Value("${jwt.SECRET_KEY}")
  private String secretKey;

  @Value("${jwt.SECRET_KEY_EXPIRATION_TIME}")
  private Long secretKeyExpirationTime;

  @Value("${jwt.REFRESH_KEY_EXPIRATION_TIME}")
  private Long refreshKeyExpirationTime;


  @Override
  public String generateToken(User user) {
    return Jwts.builder()
        .setSubject(user.getId())
        .claim("role", user.getRole().name())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + secretKeyExpirationTime))
        .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
        .compact();
  }

  @Override
  public String generateRefreshToken(User user) {
    return Jwts.builder()
        .setSubject(user.getId())
        .claim("type", "REFRESH")
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + refreshKeyExpirationTime))
        .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
        .compact();
  }

  @Override
  public String getUserIdFromRefreshToken(String refreshToken) {
    return Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
        .build()
        .parseClaimsJws(refreshToken)
        .getBody()
        .getSubject();
  }

  @Override
  public boolean isRefreshTokenValid(String refreshToken) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
          .build()
          .parseClaimsJws(refreshToken);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public boolean isTokenValid(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
  }
  }
}
