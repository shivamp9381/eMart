package com.example.eMart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ProductRequestDTO {

    @NotBlank
    private String name;

    @Min(1)
    private Double price;

    private Long categoryId;

    public ProductRequestDTO(String name, Double price, Long categoryId) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }

    public ProductRequestDTO(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
