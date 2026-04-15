package com.example.eMart.controller;

import com.example.eMart.dto.ProductRequestDTO;
import com.example.eMart.dto.ProductResponseDTO;
import com.example.eMart.entity.Product;
import com.example.eMart.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "id") String sort) {

        return ResponseEntity.ok(service.getAll(page, size, sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> search(
            @RequestParam String name) {

        return ResponseEntity.ok(service.search(name));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id,
                                                     @Valid @RequestBody ProductRequestDTO dto){
        return ResponseEntity.ok(service.update(id, dto));
    }
}
