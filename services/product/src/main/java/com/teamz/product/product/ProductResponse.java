package com.teamz.product.product;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        String description,
        double availableQuantity,
        BigDecimal price,
        List<String> productImg,
        Integer categoryId,
        String categoryName,
        String categoryDescription
) {
}
