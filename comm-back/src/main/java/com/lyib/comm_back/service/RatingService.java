package com.lyib.comm_back.service;

import java.util.List;

import com.lyib.comm_back.exception.ProductException;
import com.lyib.comm_back.model.Rating;
import com.lyib.comm_back.model.User;

public interface RatingService {

  public Rating createRating(Rating request, User user) throws ProductException;

  public List<Rating> getProductsRating(Long productId);
}
