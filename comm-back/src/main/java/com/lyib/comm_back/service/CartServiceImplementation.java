package com.lyib.comm_back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyib.comm_back.exception.ProductException;
import com.lyib.comm_back.model.Cart;
import com.lyib.comm_back.model.CartItem;
import com.lyib.comm_back.model.Product;
import com.lyib.comm_back.model.User;
import com.lyib.comm_back.repository.CartRepository;
import com.lyib.comm_back.request.AddItemRequest;

@Service
public class CartServiceImplementation implements CartService {

  private CartRepository cartRepository;
  private CartItemService cartItemService;
  private ProductService productService;

  @Autowired
  public CartServiceImplementation(CartRepository cartRepository, CartItemService cartItemService,
      ProductService productService) {
    this.cartItemService = cartItemService;
    this.cartRepository = cartRepository;
    this.productService = productService;
  }

  @Override
  public Cart createCart(User user) {
    Cart cart = new Cart();
    cart.setUser(user);
    return cartRepository.save(cart);
  }

  @Override
  public Cart findUserCart(Long userId) {
    Cart cart = cartRepository.findByUserId(userId);

    int totalPrice = 0;
    int totalDiscountedPrice = 0;
    int totalItem = 0;

    for (CartItem cartItem : cart.getCartItems()) {
      totalPrice += cartItem.getPrice();
      totalDiscountedPrice += cartItem.getDiscountedPrice();
      totalItem += cartItem.getQuantity();
    }

    cart.setTotalDiscountedPrice(totalDiscountedPrice);
    cart.setTotalItem(totalItem);
    cart.setTotalPrice(totalPrice);
    cart.setDiscount(totalPrice - totalDiscountedPrice);

    return cartRepository.save(cart);
  }

  @Override
  public String addCartItem(Long userId, AddItemRequest request) throws ProductException {
    Cart cart = cartRepository.findByUserId(userId);

    Product product = productService.findProductById(request.getProductId());
    CartItem isPresent = cartItemService.isCartItemExists(cart, product, request.getSize(), userId);

    if (isPresent == null) {
      CartItem cartItem = new CartItem();
      cartItem.setProduct(product);
      cartItem.setCart(cart);
      cartItem.setQuantity(request.getQuantity());
      cartItem.setUserId(userId);

      int price = request.getQuantity() * product.getDiscountedPrice();

      cartItem.setPrice(price);
      cartItem.setSize(request.getSize());

      CartItem createdCartItem = cartItemService.createCartItem(cartItem);
      cart.getCartItems().add(createdCartItem);
    }

    return "Предмет добавлен в корзину";

  }

}
