package com.example.eMart.service;

import com.example.eMart.entity.Category;
import com.example.eMart.exception.CategoryNotFoundException;
import com.example.eMart.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    // create
    public Category create(Category category) {
        return repository.save(category);
    }

    // get all
    public List<Category> findAll() {
        return repository.findAll();
    }

    // find by id
    public Category findById(Long id) {
        return repository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException("Category not found"));
    }
}
