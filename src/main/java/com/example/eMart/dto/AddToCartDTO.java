package com.example.eMart.dto;

public class AddToCartDTO {

    private Long productId;
    private int quantity;

    public AddToCartDTO(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public AddToCartDTO() {}

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
