package com.example.eMart.service;

import com.example.eMart.dto.AddToCartDTO;
import com.example.eMart.entity.Cart;
import com.example.eMart.entity.CartItem;
import com.example.eMart.entity.Product;
import com.example.eMart.entity.User;
import com.example.eMart.repository.CartItemRepository;
import com.example.eMart.repository.CartRepository;
import com.example.eMart.repository.ProductRepository;
import com.example.eMart.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepo;
    private final ProductRepository productRepo;
    private final CartItemRepository cartItemRepo;
    private final UserRepository userRepo;


    public CartService(CartRepository cartRepo, ProductRepository productRepo, CartItemRepository cartItemRepo, UserRepository userRepo) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
        this.cartItemRepo = cartItemRepo;
        this.userRepo = userRepo;
    }

    public String addToCart(AddToCartDTO dto) {

        // get logged in user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepo.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));

        // get or create cart
        Cart cart = cartRepo.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepo.save(newCart);
                });

        // get product
        Product product = productRepo.findById(dto.getProductId())
                .orElseThrow(()->new RuntimeException("Product not found"));

        // check if already in cart
        CartItem cartItem = cartItemRepo.findByCartAndProduct(cart, product)
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + dto.getQuantity());
        }
        else{
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(dto.getQuantity());
            cartItem.setCart(cart);
        }

        cartItemRepo.save(cartItem);

        return "Product Added to Cart";
    }


}
