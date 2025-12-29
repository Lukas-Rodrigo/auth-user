package com.lucastexeira.authuser.infra.filter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {



  private Key secretKey;

  public JwtAuthFilter(@Value("${jwt.SECRET_KEY}") String secret) {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        try {
          String token = authHeader.substring(7);

          Claims claims =
              Jwts
                  .parserBuilder()
                  .setSigningKey(secretKey).build()
                  .parseClaimsJws(token)
                  .getBody();

          String role = claims.get("role", String.class);
          List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
          UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(
                  claims.getSubject(),
                  null
                  , authorities
              );
          SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException ex) {
          SecurityContextHolder.clearContext();
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          return;
        }

    }
    filterChain.doFilter(request, response);
  }
}
