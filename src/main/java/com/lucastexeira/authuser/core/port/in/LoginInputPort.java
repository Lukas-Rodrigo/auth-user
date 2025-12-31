package com.lucastexeira.authuser.core.port.in;

import com.lucastexeira.authuser.core.usecase.result.AuthResult;

public interface LoginInputPort {

  AuthResult execute(String email, String password);

}
