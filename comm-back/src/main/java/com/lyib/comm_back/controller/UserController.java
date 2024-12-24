package com.lyib.comm_back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyib.comm_back.exception.UserException;
import com.lyib.comm_back.model.User;
import com.lyib.comm_back.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/profile")
  public ResponseEntity<User> getUserProfileHandeler(@RequestHeader("Authorization") String jwt) throws UserException {

    User user = userService.findUserByJwt(jwt);

    return new ResponseEntity<>(user, HttpStatus.OK);
  }
}
