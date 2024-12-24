package com.lyib.comm_back.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyib.comm_back.exception.ProductException;
import com.lyib.comm_back.model.Product;
import com.lyib.comm_back.model.Review;
import com.lyib.comm_back.model.User;
import com.lyib.comm_back.repository.ProductRepository;
import com.lyib.comm_back.repository.ReviewRepository;
import com.lyib.comm_back.request.ReviewRequest;

@Service
public class ReviewServiceImplementation implements ReviewService {

  private ReviewRepository reviewRepository;
  private ProductService productService;
  private ProductRepository productRepository;

  @Autowired
  public ReviewServiceImplementation(ReviewRepository reviewRepository, ProductRepository productRepository,
      ProductService productService) {
    this.reviewRepository = reviewRepository;
    this.productRepository = productRepository;
    this.productService = productService;
  }

  @Override
  public Review createReview(ReviewRequest request, User user) throws ProductException {
    Product product = productService.findProductById(request.getProductId());

    Review review = new Review();
    review.setUser(user);
    review.setProduct(product);
    review.setReview(request.getReview());
    review.setCreatedAt(LocalDateTime.now());

    return reviewRepository.save(review);
  }

  @Override
  public List<Review> getAllReview(Long productId) {
    return reviewRepository.getAllProductsReview(productId);
  }

}
