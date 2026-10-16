package com.teamz.product.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // Find cart item by cart id and product id
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = ?1 AND ci.product.id = ?2")
    CartItem findByCartIdAndProductId(Long id, Long productId);

    // Find cart items by cart id
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = ?1")
    List<CartItem> findByCartId(Long id);
}
