package com.lyib.comm_back.request;

import lombok.Data;

@Data
public class RatingRequest {

  private Long productId;
  private double rating;
}
