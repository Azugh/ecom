package com.lyib.comm_back.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lyib.comm_back.model.User;
import com.lyib.comm_back.repository.UserRepository;

@Service
public class CustomeUserService implements UserDetailsService {

  private UserRepository userRepository;

  @Autowired
  public CustomeUserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException("Такой пользователь с email не найден - " + username);
    }

    List<GrantedAuthority> authorities = new ArrayList<>();

    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
  }

}
