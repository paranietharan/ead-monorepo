package com.teamz.product.cart;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse (
        @NotNull(message = "Product id is required")
        Long productId,
        @NotNull(message = "Product name is required")
        String productName,
        @NotNull(message = "Product description is required")
        String productDescription,
        @NotNull(message = "Product price is required")
        BigDecimal productPrice,
        @NotNull(message = "Product quantity is required")
        int quantity,
        List<String> productImg
){
}
