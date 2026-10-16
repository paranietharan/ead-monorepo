package com.teamz.product.category;

import com.teamz.product.product.Product;
import com.teamz.product.product.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;
    private final ProductRepository productRepository;

    public Integer createCategory(@Valid CategoryRequest request) {
        Category category = Category.builder()
                .name(request.name())
                .description(request.description())
                .build();
        return repository.save(category).getId();
    }

    public Integer updateCategory(@Valid UpdateCategoryRequest request) {
        Category category = repository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        category.setName(request.name());
        category.setDescription(request.description());
        return repository.save(category).getId();
    }

    public List<CategoryResponse> getAllCategories() {
        return repository.findAll().stream()
                .map(category -> new CategoryResponse(
                        category.getId(),
                        category.getName(),
                        category.getDescription()
                ))
                .toList();
    }

    public Integer deleteCategory(Integer categoryId) {
        // Check if category exists
        if (!repository.existsById(categoryId)) {
            throw new IllegalArgumentException("Category not found");
        }

        // find all products in the category
        List<Product> products = productRepository.findByCategoryId(categoryId);

        // when delete product, we need to create a new category other if the category is not exist
        // they we need to assign all products that belong to the deleted category to the new category
        Category category = Category.builder()
                .name("Uncategorized")
                .description("This is the default category for products that do not belong to any category")
                .build();

        Category existingCategory = repository.findByName(category.getName());
        if (existingCategory == null) {
            repository.save(category);
        } else {
            category = existingCategory;
        }

        // change the category of all products to null
        for (Product product : products) {
            product.setCategory(category);
            productRepository.save(product);
        }

        // delete the category
        repository.deleteById(categoryId);
        return categoryId;
    }
}
