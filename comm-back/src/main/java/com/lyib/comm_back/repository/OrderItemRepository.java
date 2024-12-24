package com.lyib.comm_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lyib.comm_back.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
