package com.lyib.comm_back.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ApiResponse {

  private String message;
  private boolean status;
}
