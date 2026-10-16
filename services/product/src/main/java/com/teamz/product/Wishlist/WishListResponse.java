package com.teamz.product.Wishlist;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record WishListResponse(
        @NotNull(message = "Product id is required")
        Long productId,
        @NotNull(message = "Product name is required")
        String productName,
        @NotNull(message = "Product description is required")
        String productDescription,
        @NotNull(message = "Product price is required")
        BigDecimal productPrice,
        @Positive(message = "Product quantity must be greater than 0")
        double productQuantity,
        List<String> productImg
) {
}
