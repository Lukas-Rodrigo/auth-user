package com.lucastexeira.authuser.core.port.out.user;


public interface ExistUserByEmailOutputPort {

  boolean execute(String email);


}
