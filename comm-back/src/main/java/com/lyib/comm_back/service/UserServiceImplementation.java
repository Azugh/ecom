package com.lyib.comm_back.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lyib.comm_back.config.JwtProvider;
import com.lyib.comm_back.exception.UserException;
import com.lyib.comm_back.model.User;
import com.lyib.comm_back.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {

  private UserRepository userRepository;
  private JwtProvider jwtProvider;

  public UserServiceImplementation(UserRepository userRepository, JwtProvider jwtProvider) {
    this.userRepository = userRepository;
    this.jwtProvider = jwtProvider;
  }

  @Override
  public User findUserById(Long userId) throws UserException {
    Optional<User> user = userRepository.findById(userId);
    if (user.isPresent()) {
      return user.get();
    }
    throw new UserException("Пользователь с id не найден -" + userId);
  }

  @Override
  public User findUserByJwt(String jwt) throws UserException {
    String email = jwtProvider.getEmailFromToken(jwt);

    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new UserException("Пользователь с email не найден -" + email);
    }
    return user;
  }

}
