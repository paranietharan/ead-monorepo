package com.teamz.order.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/api/v1/products/{product-id}/check-availability")
    Boolean checkAvailability(@PathVariable("product-id") Long productId,
                              @RequestParam("quantity") double quantity);

    @PutMapping("/api/v1/products/{product-id}/update-quantity")
    Boolean updateQuantity(@PathVariable("product-id") Long productId,
                           @RequestParam("quantity") double quantity);

}
