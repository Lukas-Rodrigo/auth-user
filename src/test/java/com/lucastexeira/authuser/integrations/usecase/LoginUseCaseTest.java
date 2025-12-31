package com.lucastexeira.authuser.integrations.usecase;


import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.exception.WrongCredentialsException;
import com.lucastexeira.authuser.core.port.out.FindUserByEmailOutputPort;
import com.lucastexeira.authuser.core.port.out.PasswordEncoderOutputPort;
import com.lucastexeira.authuser.core.port.out.TokenServiceOutputPort;
import com.lucastexeira.authuser.core.usecase.LoginUseCase;
import com.lucastexeira.authuser.core.usecase.result.AuthResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginUseCaseTest {

  @Mock
  private FindUserByEmailOutputPort findUserByEmail;

  @Mock
  private PasswordEncoderOutputPort encoder;

  @Mock
  private TokenServiceOutputPort tokenService;

  @InjectMocks
  private LoginUseCase loginUseCase;

  @Test
  void shouldLoginWithCorrectEmailAndPasswordSuccessfully() {
    // Given
    String email = "user@test.com";
    String password = "password123";
    String encodedPassword = "$2a$10$encodedPassword";
    String token = "jwt.token.here";

    User user = new User();
    user.setId(UUID.randomUUID());
    user.setEmail(email);
    user.setPassword(encodedPassword);

    when(findUserByEmail.execute(email)).thenReturn(user);
    when(encoder.matches(password, encodedPassword)).thenReturn(true);
    when(tokenService.generateToken(user)).thenReturn(token);

    // When
    AuthResult result = loginUseCase.execute(email, password);

    // Then
    assertNotNull(result);
    assertEquals(token, result.getAccessToken());
    verify(findUserByEmail).execute(email);
    verify(encoder).matches(password, encodedPassword);
    verify(tokenService).generateToken(user);
  }

  @Test
  void shouldThrowExceptionWhenUserNotFound() {
    // Given
    String email = "nonexistent@test.com";
    String password = "password123";

    when(findUserByEmail.execute(email)).thenReturn(null);

    // When & Then
    WrongCredentialsException exception = assertThrows(
        WrongCredentialsException.class,
        () -> loginUseCase.execute(email, password)
    );

    assertEquals("Wrong credentials", exception.getMessage());
    verify(findUserByEmail).execute(email);
    verifyNoInteractions(encoder);
    verifyNoInteractions(tokenService);
  }

  @Test
  void shouldThrowExceptionWhenPasswordIsIncorrect() {
    // Given
    String email = "user@test.com";
    String password = "wrongPassword";
    String encodedPassword = "$2a$10$encodedPassword";

    User user = new User();
    user.setId(UUID.randomUUID());
    user.setEmail(email);
    user.setPassword(encodedPassword);

    when(findUserByEmail.execute(email)).thenReturn(user);
    when(encoder.matches(password, encodedPassword)).thenReturn(false);

    // When & Then
    WrongCredentialsException exception = assertThrows(
        WrongCredentialsException.class,
        () -> loginUseCase.execute(email, password)
    );

    assertEquals("Wrong credentials", exception.getMessage());
    verify(findUserByEmail).execute(email);
    verify(encoder).matches(password, encodedPassword);
    verifyNoInteractions(tokenService);
  }
}
