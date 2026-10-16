package com.teamz.product.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c.name FROM Category c WHERE c.name = :name")
    Category findByName(String name);
}
