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
import com.lyib.comm_back.model.Rating;
import com.lyib.comm_back.model.User;
import com.lyib.comm_back.service.RatingService;
import com.lyib.comm_back.service.UserService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

  @Autowired
  private UserService userService;

  @Autowired
  private RatingService ratingService;

  @PostMapping("/create")
  public ResponseEntity<Rating> createRating(@RequestBody Rating request,
      @RequestHeader("Authorization") String jwt) throws UserException, ProductException {

    User user = userService.findUserByJwt(jwt);

    Rating rating = ratingService.createRating(request, user);

    return new ResponseEntity<Rating>(rating, HttpStatus.OK);
  }

  @GetMapping("/product/{productId}")
  public ResponseEntity<List<Rating>> getProductsRating(@PathVariable Long productId,
      @RequestHeader("Authorization") String jwt) throws UserException, ProductException {

    User user = userService.findUserByJwt(jwt);

    List<Rating> ratings = ratingService.getProductsRating(productId);

    return new ResponseEntity<>(ratings, HttpStatus.OK);
  }
}
