package com.teamz.order.repository;

import com.teamz.order.entity.Order;
import com.teamz.order.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

        @Query("SELECT o FROM Order o WHERE o.customerId = :customerId")
        List<Order> findByCustomerId(Integer customerId);
}
