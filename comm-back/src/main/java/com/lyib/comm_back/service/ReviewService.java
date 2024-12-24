package com.lyib.comm_back.service;

import java.util.List;

import com.lyib.comm_back.exception.ProductException;
import com.lyib.comm_back.model.Review;
import com.lyib.comm_back.model.User;
import com.lyib.comm_back.request.ReviewRequest;

public interface ReviewService {

  public Review createReview(ReviewRequest request, User user) throws ProductException;

  public List<Review> getAllReview(Long productId);
}
