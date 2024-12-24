package com.lyib.comm_back.request;

import lombok.Data;

@Data
public class LoginRequest {

  private String email;
  private String password;
}
