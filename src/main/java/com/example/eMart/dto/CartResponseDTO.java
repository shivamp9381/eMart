package com.example.eMart.dto;

import java.util.List;

public class CartResponseDTO {

    private List<CartItemResponseDTO> items;
    private double totalAmount;

    public CartResponseDTO(List<CartItemResponseDTO> items, double totalAmount) {
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public CartResponseDTO() {}

    public void setItems(List<CartItemResponseDTO> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}