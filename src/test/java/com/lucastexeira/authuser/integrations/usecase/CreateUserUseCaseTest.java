package com.lucastexeira.authuser.integrations.usecase;
import com.lucastexeira.authuser.core.exception.UserAlreadyExistsException;
import com.lucastexeira.authuser.core.port.out.SaveUserOutputPort;
import com.lucastexeira.authuser.core.port.out.ExistUserByEmailOutputPort;
import com.lucastexeira.authuser.core.port.out.PasswordEncoderOutputPort;
import com.lucastexeira.authuser.core.usecase.CreateUserUseCase;
import com.lucastexeira.authuser.core.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseTest {

  @Mock
  private ExistUserByEmailOutputPort existUserByEmailOutputPort;

  @Mock
  private SaveUserOutputPort saveUserOutputPort;

  @Mock
  private PasswordEncoderOutputPort passwordEncoderOutputPort;

  @InjectMocks
  private CreateUserUseCase createUserUseCase;


  @Test
  void shouldCreateUserWhenEmailDoesNotExist() {

    when(existUserByEmailOutputPort.execute("test@gmail.com")).thenReturn(false);
    when(passwordEncoderOutputPort.encode("password123")).thenReturn("$2a$10$encodedPassword");
    when(saveUserOutputPort.execute(any(User.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

    User user = createUserUseCase.createUser(new User(null, "Test User",
        "test@gmail.com", "password123"));

    assertNotNull(user);
    assertEquals("test@gmail.com", user.getEmail());

    verify(existUserByEmailOutputPort).execute("test@gmail.com");
    verify(passwordEncoderOutputPort).encode("password123");
    verify(saveUserOutputPort).execute(any(User.class));

  }

  @Test
  void shouldReturnExceptionWhenEmailExist() {

    User user = new User(
        null,
        "Test User",
        "test@gmail.com",
        "password123"
    );

    when(existUserByEmailOutputPort.execute("test@gmail.com")).thenReturn(true);

    UserAlreadyExistsException exception =
        assertThrows(UserAlreadyExistsException.class,
            () -> createUserUseCase.createUser(user));

    assertEquals("User with email test@gmail.com already exists.", exception.getMessage());

  }
}
