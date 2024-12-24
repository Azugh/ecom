package com.lyib.comm_back.request;

import lombok.Data;

@Data
public class ReviewRequest {

  private Long productId;
  private String review;
}
