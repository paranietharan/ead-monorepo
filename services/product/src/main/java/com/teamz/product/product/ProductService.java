package com.teamz.product.product;

import com.teamz.product.category.Category;
import com.teamz.product.category.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final CategoryRepository categoryRepository;

    public Long createProduct(
            @Valid ProductRequest request
    ) throws IOException {
        var product = mapper.toProduct(request);
        return repository.save(product).getId();
    }

    public ProductResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID:: " + id));
    }

//    public Page<ProductResponse> findAll(int page, int limit, String sortBy) {
//        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(sortBy));
//        return repository.findAll(pageable).map(mapper::toProductResponse);
//    }
    public Page<ProductResponse> findAll(int page, int limit, String sortBy, Long categoryId, Double minPrice, Double maxPrice) {
    Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(sortBy));

    // Custom filtering logic
    if (categoryId != null || minPrice != null || maxPrice != null) {
        return repository.findAllWithFilters(categoryId, minPrice, maxPrice, pageable)
                .map(mapper::toProductResponse);
    }

    // Default behavior
    return repository.findAll(pageable).map(mapper::toProductResponse);
    }


    public void updateProduct(
            @Valid UpdateProductRequest request
    ) throws IOException {
        // Update the product
        Product product = repository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID:: " + request.id()));

        product.setName(request.name());
        product.setPrice(request.price());
        product.setAvailableQuantity(request.availableQuantity());
        product.setDescription(request.description());

        if(request.productImg() != null) {
            product.setProductImg(request.productImg());
        } else {
            product.setProductImg(null);
        }

        // get the category from the db
        // Update the category
        product.setCategory(
                categoryRepository.findById(request.categoryId())
                        .orElseThrow(() -> new EntityNotFoundException("Category not found with ID:: " + request.categoryId()))
        );
        // Ensure the product ID is correct and set
        System.out.println("Updating product with ID: " + product.getId());
        repository.save(product);
    }

    public void deleteProduct(Long productId) {
        repository.deleteById(productId);
    }

    public Boolean checkAvailability(Long productId, double quantity) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID:: " + productId));
        return product.getAvailableQuantity() >= quantity;
    }

    public Boolean updateQuantity(Long productId, double quantity) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID:: " + productId));
        product.setAvailableQuantity(product.getAvailableQuantity() - quantity);
        repository.save(product);
        return true;
    }
}