package com.example.eMart.controller;

import com.example.eMart.entity.Category;
import com.example.eMart.service.CategoryService;
import com.example.eMart.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    public List<Category> findAll(){
        return categoryService.findAll();
    }

    @PostMapping
    public Category create(@RequestBody Category category){
        return categoryService.create(category);
    }
}
