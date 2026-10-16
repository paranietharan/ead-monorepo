package com.teamz.product.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // Find cart by user id
    @Query("SELECT c FROM Cart c WHERE c.userId = ?1")
    Cart findByUserId(Long userId);
}
