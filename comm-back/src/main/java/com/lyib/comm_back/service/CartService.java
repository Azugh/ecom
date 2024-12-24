package com.lyib.comm_back.service;

import com.lyib.comm_back.exception.ProductException;
import com.lyib.comm_back.model.Cart;
import com.lyib.comm_back.model.User;
import com.lyib.comm_back.request.AddItemRequest;

public interface CartService {

  public Cart createCart(User user);

  public String addCartItem(Long userId, AddItemRequest request) throws ProductException;

  public Cart findUserCart(Long userId);

}
