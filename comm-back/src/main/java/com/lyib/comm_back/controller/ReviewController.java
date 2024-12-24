package com.lyib.comm_back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyib.comm_back.exception.ProductException;
import com.lyib.comm_back.exception.UserException;
import com.lyib.comm_back.model.Review;
import com.lyib.comm_back.model.User;
import com.lyib.comm_back.request.ReviewRequest;
import com.lyib.comm_back.service.ReviewService;
import com.lyib.comm_back.service.UserService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

  @Autowired
  private ReviewService reviewService;

  @Autowired
  private UserService userService;

  @PostMapping("/create")
  public ResponseEntity<Review> createReview(@RequestBody ReviewRequest request,
      @RequestHeader("Authorization") String jwt) throws UserException, ProductException {

    User user = userService.findUserByJwt(jwt);

    Review review = reviewService.createReview(request, user);

    return new ResponseEntity<>(review, HttpStatus.OK);
  }

  @GetMapping("/product/{productId}")
  public ResponseEntity<List<Review>> getProductReviews(@PathVariable Long productId)
      throws UserException, ProductException {

    List<Review> reviews = reviewService.getAllReview(productId);

    return new ResponseEntity<>(reviews, HttpStatus.OK);
  }
}
