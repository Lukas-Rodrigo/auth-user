package com.lucastexeira.authuser.infra.config;

import com.lucastexeira.authuser.core.port.in.CreateUserInputPort;
import com.lucastexeira.authuser.core.port.out.CreateUserOutputPort;
import com.lucastexeira.authuser.core.port.out.FindUserByEmailOutputPort;
import com.lucastexeira.authuser.core.usecase.CreateUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBeans {

  @Bean
  public CreateUserInputPort createUserInputPort(CreateUserOutputPort createUserOutputPort,
                                                 FindUserByEmailOutputPort findUserByEmailOutputPort) {
    return new CreateUserUseCase(createUserOutputPort, findUserByEmailOutputPort);

  }
}
