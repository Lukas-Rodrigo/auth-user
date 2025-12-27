package com.lucastexeira.authuser.adapters.in.controllers;

import com.lucastexeira.authuser.adapters.in.controllers.user.dto.UserRequest;
import com.lucastexeira.authuser.adapters.in.controllers.user.dto.UserResponse;
import com.lucastexeira.authuser.adapters.in.controllers.user.mapper.UserMapper;
import com.lucastexeira.authuser.core.port.in.CreateUserInputPort;
import com.lucastexeira.authuser.domain.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {

  private final CreateUserInputPort createUserInputPort;
  private final UserMapper userMapper;

  public UserController(CreateUserInputPort createUserInputPort, UserMapper userMapper) {
    this.createUserInputPort = createUserInputPort;
    this.userMapper = userMapper;
  }

  @PostMapping()
  public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest user) {
    User newUser = userMapper.toDomain(user);
    var createdUser = createUserInputPort.createUser(newUser);
    var response = userMapper.toResponseDTO(createdUser);
    return ResponseEntity.ok().body(response);
  }

}
