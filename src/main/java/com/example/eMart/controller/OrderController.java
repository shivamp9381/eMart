package com.example.eMart.controller;

import com.example.eMart.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder() {
        return ResponseEntity.ok(service.placeOrder());
    }
}
