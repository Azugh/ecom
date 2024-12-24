package com.lyib.comm_back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyib.comm_back.exception.ProductException;
import com.lyib.comm_back.exception.UserException;
import com.lyib.comm_back.model.Cart;
import com.lyib.comm_back.model.User;
import com.lyib.comm_back.request.AddItemRequest;
import com.lyib.comm_back.response.ApiResponse;
import com.lyib.comm_back.service.CartService;
import com.lyib.comm_back.service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

  @Autowired
  private CartService cartService;

  @Autowired
  private UserService userService;

  @GetMapping("/")
  public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
    User user = userService.findUserByJwt(jwt);
    Cart cart = cartService.findUserCart(user.getId());

    return new ResponseEntity<Cart>(cart, HttpStatus.OK);
  }

  @PutMapping("/add")
  public ResponseEntity<ApiResponse> addItemsToCart(@RequestBody AddItemRequest request,
      @RequestHeader("Authorization") String jwt) throws UserException, ProductException {

    User user = userService.findUserByJwt(jwt);

    cartService.addCartItem(user.getId(), request);

    ApiResponse res = new ApiResponse();
    res.setMessage("Товар добавлен в корзину");
    res.setStatus(true);

    return new ResponseEntity<>(res, HttpStatus.OK);
  }
}
