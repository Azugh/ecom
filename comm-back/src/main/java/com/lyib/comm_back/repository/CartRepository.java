package com.lyib.comm_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyib.comm_back.model.Cart;
import com.lyib.comm_back.model.User;

public interface CartRepository extends JpaRepository<Cart, Long> {

  @Query("SELECT c FROM Cart c WHERE c.user.id=:userid")
  public Cart findByUserId(@Param("userid") Long userId);

}
