package com.teamz.product.Wishlist;

import com.teamz.product.product.Product;
import com.teamz.product.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;

    public Integer addProductToWishList(Long productId, Long userId) {
        WishList wishList = wishListRepository.findByUserId(userId)
                .orElseGet(() -> {
                    WishList newWishList = new WishList();
                    newWishList.setUserId(userId);
                    return wishListRepository.save(newWishList);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (!wishList.getWishListItems().contains(product)) {
            wishList.getWishListItems().add(product);
            wishListRepository.save(wishList);
        }

        return wishList.getWishListItems().size();
    }

    public Integer removeProductFromWishList(Long productId, Long userId) {
        WishList wishList = wishListRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (wishList.getWishListItems().contains(product)) {
            wishList.getWishListItems().remove(product);
            wishListRepository.save(wishList);
        }

        return wishList.getWishListItems().size();
    }

    public List<WishListResponse> getWishListItems(Long userId) {
        WishList wishList = wishListRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist not found"));

        return wishList.getWishListItems().stream()
                .map(product -> new WishListResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getAvailableQuantity(),
                        product.getProductImg()
                ))
                .toList();
    }
}