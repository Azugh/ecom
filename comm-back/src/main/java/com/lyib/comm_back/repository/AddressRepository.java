package com.lyib.comm_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lyib.comm_back.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
