package com.example.eMart.dto;

public class UpdateCartDTO {

    private Long cartItemId;
    private int quantity;

    public UpdateCartDTO(Long cartItemId, int quantity) {
        this.cartItemId = cartItemId;
        this.quantity = quantity;
    }

    public UpdateCartDTO() {}

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
