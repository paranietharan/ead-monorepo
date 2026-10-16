package com.teamz.product.category;

import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        @NotNull(message = "Category name cannot be null")
        String name,
        String description
) {
}
