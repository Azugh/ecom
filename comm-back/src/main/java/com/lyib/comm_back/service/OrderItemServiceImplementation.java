package com.lyib.comm_back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyib.comm_back.model.OrderItem;
import com.lyib.comm_back.repository.OrderItemRepository;

@Service
public class OrderItemServiceImplementation implements OrderItemService {

  @Autowired
  private OrderItemRepository orderItemRepository;

  @Override
  public OrderItem createOrderItem(OrderItem orderItem) {

    return orderItemRepository.save(orderItem);
  }

}
