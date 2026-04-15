package com.example.eMart.dto;

import com.example.eMart.entity.Category;

public class ProductResponseDTO {

    private Long id;
    private String name;
    private Double price;
    private String categoryName;

    public ProductResponseDTO(Long id, String name, Double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryName = category;
    }

    public ProductResponseDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String category) {
        this.categoryName = category;
    }

}
