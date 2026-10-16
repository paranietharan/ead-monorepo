package com.teamz.product.category;

import jakarta.validation.constraints.NotNull;

public record UpdateCategoryRequest(
        @NotNull(message = "Category id is required")
        Integer id,
        @NotNull(message = "Category name is required")
        String name,
        String description
) {
}
