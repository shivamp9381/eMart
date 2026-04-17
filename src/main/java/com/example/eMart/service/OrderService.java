package com.example.eMart.service;

import com.example.eMart.entity.*;
import com.example.eMart.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final UserRepository userRepo;

    public OrderService(CartRepository cartRepo,
                        CartItemRepository cartItemRepo,
                        OrderRepository orderRepo,
                        OrderItemRepository orderItemRepo,
                        UserRepository userRepo) {
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.userRepo = userRepo;
    }

    public String placeOrder() {

        // 🔐 1. Get logged-in user
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🛒 2. Get cart
        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> cartItems = cartItemRepo.findByCart(cart);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 📦 3. Create Order
        Order order = new Order();
        order.setUser(user);

        double totalAmount = 0;

        Order savedOrder = orderRepo.save(order);

        // 📦 4. Convert cart items → order items
        for (CartItem item : cartItems) {

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());

            orderItemRepo.save(orderItem);

            totalAmount += item.getProduct().getPrice() * item.getQuantity();
        }

        // 💰 5. Update total
        savedOrder.setTotalAmount(totalAmount);
        orderRepo.save(savedOrder);

        // 🧹 6. Clear cart
        cartItemRepo.deleteAll(cartItems);

        return "Order placed successfully";
    }
}