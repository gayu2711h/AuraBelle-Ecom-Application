package com.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>
{

}
