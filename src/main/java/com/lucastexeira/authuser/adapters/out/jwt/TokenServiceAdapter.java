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

  @Value("${jwt.EXPIRATION_TIME}")
  private Long expirationTime;


  @Override
  public String generateToken(User user) {
    return Jwts.builder()
        .setSubject(user.getId())
        .claim("role", user.getRole().name())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
        .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
        .compact();
  }
}
