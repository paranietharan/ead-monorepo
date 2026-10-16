package com.teamz.order.repository;

import com.teamz.order.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {

    @Query("SELECT ol FROM OrderLine ol WHERE ol.order.id = :orderId")
    List<OrderLine> findByOrder(Integer orderId);

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderLine ol WHERE ol.order.id = :orderId")
    void deleteByOrderId(Integer orderId);

}
