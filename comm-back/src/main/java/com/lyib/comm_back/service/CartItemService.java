package com.lyib.comm_back.service;

import com.lyib.comm_back.exception.CartItemException;
import com.lyib.comm_back.exception.UserException;
import com.lyib.comm_back.model.Cart;
import com.lyib.comm_back.model.CartItem;
import com.lyib.comm_back.model.Product;

public interface CartItemService {

  public CartItem createCartItem(CartItem cartItem);

  public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;

  public CartItem isCartItemExists(Cart cart, Product product, String size, Long userId);

  public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;

  public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
