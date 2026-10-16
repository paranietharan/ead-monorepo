package com.teamz.product.category;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    // Add category - checked
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Integer> createCategory(
            @RequestBody @Valid CategoryRequest request
    ) {
        return ResponseEntity.ok(categoryService.createCategory(request));
    }

    // Update category details - checked
    @PutMapping
    public ResponseEntity<Integer> updateCategory(
            @RequestBody @Valid UpdateCategoryRequest request
    ) {
        return ResponseEntity.ok(categoryService.updateCategory(request));
    }

    // Get all categories -checked
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // Delete category - checked
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Integer> deleteCategory(
            @PathVariable Integer categoryId
    ) {
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }
}
