package com.teamz.product.Wishlist;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/wishlist")
public class WishListController {

    private final WishListService wishListService;

    // Add a product to the wishlist
    @PostMapping("/add")
    public ResponseEntity<Integer> addProductToWishList(
            @RequestParam("productId") Long productId,
            @RequestParam("userId") Long userId
    ){
        return ResponseEntity.ok(wishListService.addProductToWishList(productId, userId));
    }

    // Remove a product from the wishlist
    @PostMapping("/remove")
    public ResponseEntity<Integer> removeProductFromWishList(
            @RequestParam("productId") Long productId,
            @RequestParam("userId") Long userId
    ){
        return ResponseEntity.ok(wishListService.removeProductFromWishList(productId, userId));
    }

    // Get all products in the wishlist
    @GetMapping
    public ResponseEntity<List<WishListResponse>> getWishListItems(
            @RequestParam("userId") Long userId
    ){
        return ResponseEntity.ok(wishListService.getWishListItems(userId));
    }
}
