package com.example.eMart.repository;

import com.example.eMart.entity.Cart;
import com.example.eMart.entity.CartItem;
import com.example.eMart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    List<CartItem> findByCart(Cart cart);
}