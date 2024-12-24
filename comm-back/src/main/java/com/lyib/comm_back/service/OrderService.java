package com.lyib.comm_back.service;

import java.util.List;

import com.lyib.comm_back.exception.OrderException;
import com.lyib.comm_back.model.Address;
import com.lyib.comm_back.model.Order;
import com.lyib.comm_back.model.User;

public interface OrderService {

  public Order CreateOrder(User user, Address shippingAddress);

  public Order findOrderById(Long orderId) throws OrderException;

  public List<Order> userOrderHistory(Long orderId);

  public List<Order> getAllOrders();

  public Order shippedOrder(Long orderId) throws OrderException;

  public Order placedOrder(Long orderId) throws OrderException;

  public Order confirmedOrder(Long orderId) throws OrderException;

  public Order deliveredOrder(Long orderId) throws OrderException;

  public Order canceledOrder(Long orderId) throws OrderException;

  public void deleteOrder(Long orderId) throws OrderException;

}
