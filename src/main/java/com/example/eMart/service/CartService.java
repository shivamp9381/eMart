package com.example.eMart.service;

import com.example.eMart.dto.AddToCartDTO;
import com.example.eMart.dto.CartItemResponseDTO;
import com.example.eMart.dto.CartResponseDTO;
import com.example.eMart.dto.UpdateCartDTO;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepo;
    private final ProductRepository productRepo;
    private final CartItemRepository cartItemRepo;
    private final UserRepository userRepo;

    public CartService(CartRepository cartRepo,
                       ProductRepository productRepo,
                       CartItemRepository cartItemRepo,
                       UserRepository userRepo) {
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

    public CartResponseDTO viewCart() {

        // 🔐 1. Get logged-in user
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🛒 2. Get cart
        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // 📦 3. Get cart items
        List<CartItem> cartItems = cartItemRepo.findByCart(cart);

        // 🔄 4. Convert to DTO
        List<CartItemResponseDTO> itemDTOs = new ArrayList<>();

        double totalAmount = 0;

        for (CartItem item : cartItems) {

            CartItemResponseDTO dto = new CartItemResponseDTO();

            dto.setProductName(item.getProduct().getName());
            dto.setPrice(item.getProduct().getPrice());
            dto.setQuantity(item.getQuantity());

            double itemTotal = item.getQuantity() * item.getProduct().getPrice();
            dto.setTotal(itemTotal);

            totalAmount += itemTotal;

            itemDTOs.add(dto);
        }

        // 📊 5. Final response
        CartResponseDTO response = new CartResponseDTO();
        response.setItems(itemDTOs);
        response.setTotalAmount(totalAmount);

        return response;
    }

    public String updateCart(UpdateCartDTO dto) {

        // 🔐 1. Get logged-in user
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🛒 2. Get cart
        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // 📦 3. Get cart item
        CartItem cartItem = cartItemRepo.findById(dto.getCartItemId())
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        // 🔒 4. SECURITY CHECK (VERY IMPORTANT)
        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("Unauthorized action");
        }

        // 🔄 5. Update quantity
        if (dto.getQuantity() <= 0) {
            cartItemRepo.delete(cartItem);
            return "Item removed from cart";
        }

        cartItem.setQuantity(dto.getQuantity());
        cartItemRepo.save(cartItem);

        return "Cart updated successfully";
    }

    public String removeItem(Long cartItemId) {

        // 🔐 1. Get logged-in user
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🛒 2. Get cart
        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // 📦 3. Get cart item
        CartItem cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        // 🔒 4. SECURITY CHECK
        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("Unauthorized action");
        }

        // ❌ 5. Delete
        cartItemRepo.delete(cartItem);

        return "Item removed successfully";
    }
}
