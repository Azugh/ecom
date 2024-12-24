package com.lyib.comm_back.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lyib.comm_back.exception.ProductException;
import com.lyib.comm_back.model.Product;
import com.lyib.comm_back.model.Rating;
import com.lyib.comm_back.model.User;
import com.lyib.comm_back.repository.RatingRepository;

@Service
public class RatingServiceImplementation implements RatingService {

  RatingRepository ratingRepository;
  private ProductService productService;

  public RatingServiceImplementation(RatingRepository ratingRepository, ProductService productService) {
    this.productService = productService;
    this.ratingRepository = ratingRepository;
  }

  @Override
  public Rating createRating(Rating request, User user) throws ProductException {
    Product product = productService.findProductById(request.getProduct().getId());

    Rating rating = new Rating();
    rating.setProduct(product);
    rating.setUser(user);
    rating.setRating(request.getRating());
    rating.setCreatedAt(LocalDateTime.now());

    return ratingRepository.save(rating);
  }

  @Override
  public List<Rating> getProductsRating(Long productId) {
    return ratingRepository.getAllProductsRating(productId);
  }
}
