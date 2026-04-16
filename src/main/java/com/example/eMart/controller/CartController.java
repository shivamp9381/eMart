package com.example.eMart.controller;

import com.example.eMart.dto.AddToCartDTO;
import com.example.eMart.entity.Product;
import com.example.eMart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProductToCart(@RequestBody AddToCartDTO dto) {
        return ResponseEntity.ok(cartService.addToCart(dto));
    }
}
