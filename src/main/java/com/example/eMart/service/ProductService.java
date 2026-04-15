package com.example.eMart.service;

import com.example.eMart.dto.ProductRequestDTO;
import com.example.eMart.dto.ProductResponseDTO;
import com.example.eMart.entity.Category;
import com.example.eMart.entity.Product;
import com.example.eMart.exception.CategoryNotFoundException;
import com.example.eMart.exception.ProductNotFoundException;
import com.example.eMart.repository.CategoryRepository;
import com.example.eMart.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    public ProductService(ProductRepository productRepo, CategoryRepository categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    // create
    public ProductResponseDTO create(ProductRequestDTO dto) {
        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(()-> new CategoryNotFoundException("Category not found"));

        Product product = new Product();
        product.setCategory(category);
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());

        return mapToDto(productRepo.save(product));
    }

    // get all
    public List<ProductResponseDTO> getAll(int page, int size, String sortBy) {

        Page<Product> pageData = productRepo.findAll(PageRequest.of(page, size, Sort.by(sortBy)));

        return pageData.getContent().stream()
                .map(this::mapToDto)
                .toList();
    }

    // find by id
    public ProductResponseDTO findById(Long id) {

        return mapToDto(productRepo.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found")));
    }

    // search by name
    public List<ProductResponseDTO> search(String name) {

        return productRepo.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // update
    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {

        Product existingProduct =  productRepo.findById(id).orElseThrow(()-> new ProductNotFoundException("Product not found"));

        existingProduct.setName(dto.getName());
        existingProduct.setPrice(dto.getPrice());

        if(dto.getCategoryId() != null) {
            Category category = categoryRepo.findById(dto.getCategoryId())
                    .orElseThrow(()-> new CategoryNotFoundException("Category not found"));

            existingProduct.setCategory(category);
        }

        Product updatedProduct = productRepo.save(existingProduct);

        return mapToDto(updatedProduct);
    }

    // delete
    public String delete(Long id) {

        Product product = productRepo.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));

        productRepo.delete(product);
        return "Product deleted Succesfully";
    }

    // map function
    private ProductResponseDTO mapToDto(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setCategoryName(product.getCategory().getName());

        return productResponseDTO;
    }
}
