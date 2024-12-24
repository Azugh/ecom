package com.lyib.comm_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lyib.comm_back.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

  public User findByEmail(String email);
}
