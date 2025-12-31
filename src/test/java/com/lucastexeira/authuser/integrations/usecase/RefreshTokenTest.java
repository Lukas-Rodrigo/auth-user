package com.lucastexeira.authuser.integrations.usecase;

import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.exception.InvalidRefreshTokenException;
import com.lucastexeira.authuser.core.port.out.FindUserByIdOutputPort;
import com.lucastexeira.authuser.core.port.out.TokenServiceOutputPort;
import com.lucastexeira.authuser.core.usecase.RefreshTokenUseCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenTest {

  @Mock
  private FindUserByIdOutputPort findUserByIdOutputPort;

  @Mock
  private TokenServiceOutputPort tokenService;

  @InjectMocks
  private RefreshTokenUseCase refreshTokenUseCase;

  void should1() {
    String validAccessToken = "valid.access.token";
    String validRefreshToken = "valid.refresh.token";
    String email = "validemail@gmail.com";
    String password = "ValidPassword123";
    String name = "Valid Name";
    UUID userId = UUID.fromString("valid-user-id");
    User user = new User(userId, email, password, name);


    when(tokenService.isRefreshTokenValid(validRefreshToken)).thenReturn(true);
    when(findUserByIdOutputPort.execute(userId)).thenReturn(user);
    when(tokenService.generateRefreshToken(user)).thenReturn(validAccessToken);

    String accessToken = refreshTokenUseCase.execute(validRefreshToken);

    assertNotNull(accessToken);

    assertEquals(validAccessToken, accessToken);
    assertDoesNotThrow(() -> refreshTokenUseCase.execute(validRefreshToken));
  }

  void should2() {
    String validRefreshToken = "valid.refresh.token";
    when(tokenService.isRefreshTokenValid(validRefreshToken)).thenReturn(false);

    InvalidRefreshTokenException exception = assertThrows(
        InvalidRefreshTokenException.class,
        () -> refreshTokenUseCase.execute(validRefreshToken)
    );

    assertEquals("Invalid refresh token error.", exception.getMessage());
    verify(tokenService).isRefreshTokenValid(validRefreshToken);
    verifyNoInteractions(tokenService.generateToken(any()));
    verifyNoInteractions(tokenService.getUserIdFromRefreshToken(any()));
  }


}
