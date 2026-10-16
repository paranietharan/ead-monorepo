package com.teamz.product.product;


import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public record UpdateProductRequest(
        @NotNull(message = "Product ID is required")
        Long id,
        String name,
        String description,
        double availableQuantity,
        BigDecimal price,
        Integer categoryId,
        List<String> productImg
) {
}
