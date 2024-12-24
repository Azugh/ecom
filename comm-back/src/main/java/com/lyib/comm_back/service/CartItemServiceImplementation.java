package com.lyib.comm_back.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyib.comm_back.exception.CartItemException;
import com.lyib.comm_back.exception.UserException;
import com.lyib.comm_back.model.Cart;
import com.lyib.comm_back.model.CartItem;
import com.lyib.comm_back.model.Product;
import com.lyib.comm_back.model.User;
import com.lyib.comm_back.repository.CartItemRepository;
import com.lyib.comm_back.repository.CartRepository;

@Service
public class CartItemServiceImplementation implements CartItemService {

  private CartItemRepository cartItemRepository;
  private UserService userService;
  private CartRepository cartRepository;

  @Autowired
  public CartItemServiceImplementation(CartItemRepository cartItemRepository, UserService userService,
      CartRepository cartRepository) {
    this.cartRepository = cartRepository;
    this.userService = userService;
    this.cartItemRepository = cartItemRepository;
  }

  @Override
  public CartItem createCartItem(CartItem cartItem) {
    cartItem.setQuantity(1);
    cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
    cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());

    CartItem createdCartItem = cartItemRepository.save(cartItem);
    return createdCartItem;
  }

  @Override
  public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
    CartItem item = findCartItemById(id);
    User user = userService.findUserById(item.getUserId());

    if (user.getId().equals(userId)) {
      item.setQuantity(item.getQuantity());
      item.setPrice(item.getQuantity() * item.getProduct().getPrice());
      item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());
    }
    return cartItemRepository.save(item);
  }

  @Override
  public CartItem isCartItemExists(Cart cart, Product product, String size, Long userId) {
    CartItem cartItem = cartItemRepository.isCartItemExists(cart, product, size, userId);
    return cartItem;
  }

  @Override
  public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
    CartItem cartItem = findCartItemById(cartItemId);

    User user = userService.findUserById(cartItem.getUserId());

    User rUser = userService.findUserById(userId);

    if (user.getId().equals(rUser.getId())) {
      cartItemRepository.deleteById(cartItemId);
    } else {
      throw new UserException("Вы не можете удалить товары другого пользователя");
    }
  }

  @Override
  public CartItem findCartItemById(Long cartItemId) throws CartItemException {
    Optional<CartItem> optional = cartItemRepository.findById(cartItemId);

    if (optional.isPresent()) {
      return optional.get();
    }
    throw new CartItemException("Cartitem not found with id " + cartItemId);
  }

}
