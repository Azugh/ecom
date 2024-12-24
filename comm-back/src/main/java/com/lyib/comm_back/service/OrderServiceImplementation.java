package com.lyib.comm_back.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyib.comm_back.exception.OrderException;
import com.lyib.comm_back.model.Address;
import com.lyib.comm_back.model.Cart;
import com.lyib.comm_back.model.CartItem;
import com.lyib.comm_back.model.Order;
import com.lyib.comm_back.model.OrderItem;
import com.lyib.comm_back.model.User;
import com.lyib.comm_back.repository.AddressRepository;
import com.lyib.comm_back.repository.CartRepository;
import com.lyib.comm_back.repository.OrderItemRepository;
import com.lyib.comm_back.repository.OrderRepository;
import com.lyib.comm_back.repository.UserRepository;

@Service
public class OrderServiceImplementation implements OrderService {

  private CartRepository cartRepository;
  // private CartItemService cartItemService;
  private ProductService productService;
  private AddressRepository addressRepository;
  private OrderItemRepository orderItemRepository;
  private OrderItemService orderItemService;
  private UserRepository userRepository;
  private OrderRepository orderRepository;
  private CartService cartService;

  @Autowired
  public OrderServiceImplementation(CartRepository cartRepository,
      // CartItemService cartItemService,
      AddressRepository addressRepository,
      ProductService productService,
      UserRepository userRepository,
      OrderRepository orderRepository,
      CartService cartService) {
    // this.cartItemService = cartItemService;
    this.cartRepository = cartRepository;
    this.productService = productService;
    this.addressRepository = addressRepository;
    this.userRepository = userRepository;
    this.orderRepository = orderRepository;
    this.cartService = cartService;
  }

  @Override
  public Order CreateOrder(User user, Address shippingAddress) {
    shippingAddress.setUser(user);
    Address address = addressRepository.save(shippingAddress);
    user.getAddresses().add(address);
    userRepository.save(user);

    Cart cart = cartService.findUserCart(user.getId());
    List<OrderItem> orderItems = new ArrayList<>();

    for (CartItem item : cart.getCartItems()) {
      OrderItem orderItem = new OrderItem();

      orderItem.setPrice(item.getPrice());
      orderItem.setProduct(item.getProduct());
      orderItem.setQuantity(item.getQuantity());
      orderItem.setSize(item.getSize());
      orderItem.setUserId(item.getUserId());
      orderItem.setDiscountedPrice(item.getDiscountedPrice());

      OrderItem createdOrderItem = orderItemRepository.save(orderItem);

      orderItems.add(createdOrderItem);
    }

    Order createdOrder = new Order();
    createdOrder.setUser(user);
    createdOrder.setOrderItems(orderItems);
    createdOrder.setTotalPrice(cart.getTotalPrice());
    createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
    createdOrder.setDiscount(cart.getDiscount());
    createdOrder.setTotalItem(cart.getTotalItem());

    createdOrder.setShippingAddress(address);
    createdOrder.setOrderTime(LocalDateTime.now());
    createdOrder.setOrderStatus("PENDING");
    createdOrder.getPaymentDetails().setStatus("PENDING");
    createdOrder.setCreatedAt(LocalDateTime.now());

    Order savedOrder = orderRepository.save(createdOrder);

    for (OrderItem item : orderItems) {
      item.setOrder(savedOrder);
      orderItemRepository.save(item);
    }

    return savedOrder;
  }

  @Override
  public Order findOrderById(Long orderId) throws OrderException {
    Optional<Order> optional = orderRepository.findById(orderId);

    if (optional.isPresent()) {
      return optional.get();
    }
    throw new OrderException("Заказа с таким Id не существует" + orderId);
  }

  @Override
  public List<Order> userOrderHistory(Long orderId) {
    List<Order> orders = orderRepository.getUsersOrder(orderId);
    return orders;
  }

  @Override
  public Order placedOrder(Long orderId) throws OrderException {
    Order order = findOrderById(orderId);
    order.setOrderStatus("PLACED");
    order.getPaymentDetails().setStatus("COMPLETED");
    return orderRepository.save(order);
  }

  @Override
  public Order confirmedOrder(Long orderId) throws OrderException {
    Order order = findOrderById(orderId);
    order.setOrderStatus("CONFIRMED");
    return orderRepository.save(order);
  }

  @Override
  public Order deliveredOrder(Long orderId) throws OrderException {
    Order order = findOrderById(orderId);
    order.setOrderStatus("DELIVERED");
    return orderRepository.save(order);
  }

  @Override
  public Order canceledOrder(Long orderId) throws OrderException {
    Order order = findOrderById(orderId);
    order.setOrderStatus("CANCELED");
    return orderRepository.save(order);
  }

  @Override
  public List<Order> getAllOrders() {
    return orderRepository.findAll();
  }

  @Override
  public Order shippedOrder(Long orderId) throws OrderException {
    Order order = findOrderById(orderId);
    order.setOrderStatus("SHIPPED");
    return orderRepository.save(order);
  }

  @Override
  public void deleteOrder(Long orderId) throws OrderException {
    Order order = findOrderById(orderId);

    orderRepository.deleteById(orderId);
  }

}
