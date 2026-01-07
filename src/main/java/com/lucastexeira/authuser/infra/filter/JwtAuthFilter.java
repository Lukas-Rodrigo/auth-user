package com.lucastexeira.authuser.infra.filter;


import com.lucastexeira.authuser.core.port.out.authentication.TokenServiceOutputPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final Key secretKey;

  private final TokenServiceOutputPort tokenService;

  public JwtAuthFilter(
      @Value("${jwt.SECRET_KEY}") String secret,
      TokenServiceOutputPort tokenService
  ) {
    this.secretKey = Keys.hmacShaKeyFor(
        secret.getBytes(StandardCharsets.UTF_8));
    this.tokenService = tokenService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {

    String token = extractTokenFromCookie(request);

    if (token != null && this.tokenService.isTokenValid(token)) {
      try {
        Authentication authentication = getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (JwtException ex) {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }

    }
    filterChain.doFilter(request, response);
  }


  private Authentication getAuthentication(String token) {
    Claims claims = parseClaims(token);

    String role = claims.get("role", String.class);
    List<GrantedAuthority> authorities = List.of(
        new SimpleGrantedAuthority("ROLE_" + role));

    return new UsernamePasswordAuthenticationToken(
        claims.getSubject(),
        null
        , authorities
    );
  }

  private Claims parseClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private String extractTokenFromCookie(HttpServletRequest request) {
    if (request.getCookies() == null) {
      return null;
    }

    return Arrays.stream(request.getCookies())
        .filter(cookie -> "access_token".equals(cookie.getName()))
        .map(Cookie::getValue)
        .findFirst()
        .orElse(null);
  }
}
