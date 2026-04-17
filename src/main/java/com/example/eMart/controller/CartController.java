package com.example.eMart.controller;

import com.example.eMart.dto.AddToCartDTO;
import com.example.eMart.dto.CartResponseDTO;
import com.example.eMart.dto.UpdateCartDTO;
import com.example.eMart.entity.Product;
import com.example.eMart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<CartResponseDTO> viewCart() {
        return ResponseEntity.ok(cartService.viewCart());
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCart(@RequestBody UpdateCartDTO dto) {
        return ResponseEntity.ok(cartService.updateCart(dto));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> removeItem(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.removeItem(id));
    }
}
