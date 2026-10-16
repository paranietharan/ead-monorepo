package com.teamz.product.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    // Adding a new product -tested
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Long> createProduct(
            @ModelAttribute @Valid ProductRequest request
    ) throws IOException {
        return ResponseEntity.ok(service.createProduct(request));
    }

    // Getting a product by id - tested
    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> findById(
            @PathVariable("product-id") Long productId
    ) {
        return ResponseEntity.ok(service.findById(productId));
    }

    // Getting all products - tested
//    @GetMapping
//    public ResponseEntity<Page<ProductResponse>> findAll(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "100") int limit,
//            @RequestParam(defaultValue = "id") String sortBy
//    ) {
//        return ResponseEntity.ok(service.findAll(page, limit, sortBy));
//    }
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "100") int limit,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return ResponseEntity.ok(service.findAll(page, limit, sortBy, categoryId, minPrice, maxPrice));
    }

    // Updating a product
    @PutMapping
    public ResponseEntity<Void> updateProduct(
            @ModelAttribute @Valid UpdateProductRequest request
    ) throws IOException {
        service.updateProduct(request);
        return ResponseEntity.noContent().build();
    }

    // Deleting a product
    @DeleteMapping("/{product-id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable("product-id") Long productId
    ) {
        service.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{product-id}/check-availability")
    Boolean checkAvailability(
            @PathVariable("product-id") Long productId,
            @RequestParam("quantity") double quantity
    ) {
        return service.checkAvailability(productId, quantity);
    }

    @PutMapping("/{product-id}/update-quantity")
    Boolean updateQuantity(
            @PathVariable("product-id") Long productId,
            @RequestParam("quantity") double quantity
    ) {
        return service.updateQuantity(productId, quantity);
    }
}
