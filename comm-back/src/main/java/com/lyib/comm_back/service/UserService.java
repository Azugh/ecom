package com.lyib.comm_back.service;

import com.lyib.comm_back.exception.UserException;
import com.lyib.comm_back.model.User;

public interface UserService {

  public User findUserById(Long userId) throws UserException;

  public User findUserByJwt(String jwt) throws UserException;
}
