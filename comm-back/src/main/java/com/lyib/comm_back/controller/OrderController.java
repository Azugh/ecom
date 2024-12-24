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

import com.lyib.comm_back.exception.OrderException;
import com.lyib.comm_back.exception.UserException;
import com.lyib.comm_back.model.Address;
import com.lyib.comm_back.model.Order;
import com.lyib.comm_back.model.User;
import com.lyib.comm_back.service.OrderService;
import com.lyib.comm_back.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private UserService userService;

  @PostMapping("/")
  public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,
      @RequestHeader("Authorization") String jwt) throws UserException {

    User user = userService.findUserByJwt(jwt);

    Order order = orderService.CreateOrder(user, shippingAddress);

    return new ResponseEntity<Order>(order, HttpStatus.OK);
  }

  @GetMapping("/user")
  public ResponseEntity<List<Order>> userOrderHistory(@RequestHeader("Authorization") String jwt) throws UserException {

    User user = userService.findUserByJwt(jwt);

    List<Order> orders = orderService.userOrderHistory(user.getId());

    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Order> findOrderById(@PathVariable("id") Long orderId,
      @RequestHeader("Authorization") String jwt) throws UserException, OrderException {

    User user = userService.findUserByJwt(jwt);

    Order order = orderService.findOrderById(user.getId());

    return new ResponseEntity<>(order, HttpStatus.OK);
  }
}
